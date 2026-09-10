package com.praveen.praveenmart.service.chat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Real AI provider calling Google Gemini API server-side with strict timeouts and degraded fallback.
 */
public class GeminiChatProvider implements ChatProvider {

    private static final Logger logger = LoggerFactory.getLogger(GeminiChatProvider.class);
    private static final int TIMEOUT_MS = 5000;
    private final MockChatProvider fallbackProvider = new MockChatProvider();

    private static final String SYSTEM_PROMPT = """
            You are the smart, polite, and helpful AI Shopping Assistant for PraveenMart, an Indian multi-seller e-commerce marketplace.
            Only answer questions related to the marketplace, shopping, product listings, orders, shipping, payment methods, returns, or becoming a seller.
            Keep your answers concise, clear, and friendly (1-3 sentences). Do not answer unrelated topics.
            """;

    @Override
    public String getReply(String userMessage, String context) {
        String apiKey = System.getProperty("gemini.api.key", System.getenv("GEMINI_API_KEY"));
        if (apiKey == null || apiKey.trim().isEmpty()) {
            logger.warn("GEMINI_API_KEY is not set. Falling back to MockChatProvider.");
            return fallbackProvider.getReply(userMessage, context);
        }

        try {
            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey.trim();
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            conn.setDoOutput(true);

            // Construct Gemini payload
            JsonObject payload = new JsonObject();
            JsonArray contents = new JsonArray();
            JsonObject contentObj = new JsonObject();
            JsonArray parts = new JsonArray();

            JsonObject sysPart = new JsonObject();
            sysPart.addProperty("text", SYSTEM_PROMPT + "\nContext: " + (context != null ? context : "PraveenMart Marketplace") + "\nUser Question: " + userMessage);
            parts.add(sysPart);

            contentObj.add("parts", parts);
            contents.add(contentObj);
            payload.add("contents", contents);

            byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            int statusCode = conn.getResponseCode();
            if (statusCode >= 200 && statusCode < 300) {
                try (InputStream is = conn.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    StringBuilder responseStr = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseStr.append(line);
                    }
                    JsonObject json = com.praveen.praveenmart.util.JsonUtil.getGson().fromJson(responseStr.toString(), JsonObject.class);
                    String candidateText = json.getAsJsonArray("candidates")
                            .get(0).getAsJsonObject()
                            .getAsJsonObject("content")
                            .getAsJsonArray("parts")
                            .get(0).getAsJsonObject()
                            .get("text").getAsString();
                    if (candidateText != null && !candidateText.trim().isEmpty()) {
                        return candidateText.trim();
                    }
                }
            } else {
                logger.warn("Gemini API returned HTTP status {}. Using fallback.", statusCode);
            }
        } catch (Exception e) {
            logger.warn("Gemini call failed or timed out: {}. Providing degraded static fallback.", e.getMessage());
        }

        // Return static degraded fallback (Section 11 requirement)
        return fallbackProvider.getReply(userMessage, context);
    }
}
