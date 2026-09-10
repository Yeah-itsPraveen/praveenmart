package com.praveen.praveenmart.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.praveen.praveenmart.dto.ApiResponse;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.service.chat.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import com.praveen.praveenmart.util.JsonUtil;

/**
 * Controller endpoint for AI chatbot queries (Section 11 & 17).
 * Enforces per-session rate limiting (10 requests/minute) and returns structured JSON.
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/api/v1/chat", "/api/chat"})
public class ChatServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ChatServlet.class);
    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static final String RATE_LIMIT_ATTR = "CHAT_RATE_LIMIT_QUEUE";

    private final Gson gson = JsonUtil.getGson();
    private ChatService chatService = new ChatService();

    @Override
    public void init() {
        this.chatService = new ChatService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(true);

        // Guardrail: Enforce per-session rate limit (10 messages/minute)
        if (!checkRateLimit(session)) {
            response.setStatus(429); // 429 Too Many Requests
            ApiResponse<?> err = ApiResponse.fail("RATE_LIMIT_EXCEEDED", "Too many messages. Rate limit is 10 messages per minute.");
            response.getWriter().write(gson.toJson(err));
            return;
        }

        String userMessage = extractMessage(request);

        if (userMessage == null || userMessage.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ApiResponse<?> err = ApiResponse.fail("VALIDATION_ERROR", "Message is required.");
            response.getWriter().write(gson.toJson(err));
            return;
        }

        try {
            String reply = chatService.askChatbot(userMessage, session);

            Map<String, Object> data = new HashMap<>();
            data.put("reply", reply);

            // Structure envelope with reply for dual compatibility
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("reply", reply);
            JsonObject dataObj = new JsonObject();
            dataObj.addProperty("reply", reply);
            jsonResponse.add("data", dataObj);
            jsonResponse.add("error", null);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse.toString());

        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ApiResponse<?> err = ApiResponse.fail("VALIDATION_ERROR", e.getMessage());
            response.getWriter().write(gson.toJson(err));
        } catch (Exception e) {
            logger.error("Unexpected error in chat endpoint", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ApiResponse<?> err = ApiResponse.fail("SERVER_ERROR", "Shopping assistant is temporarily unavailable.");
            response.getWriter().write(gson.toJson(err));
        }
    }

    private String extractMessage(HttpServletRequest request) {
        // Support both application/json body and form param "message"
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            try (BufferedReader reader = request.getReader()) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String body = sb.toString().trim();
                if (!body.isEmpty()) {
                    JsonObject json = com.praveen.praveenmart.util.JsonUtil.getGson().fromJson(body, JsonObject.class);
                    if (json != null && json.has("message")) {
                        return json.get("message").getAsString();
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return request.getParameter("message");
    }

    private synchronized boolean checkRateLimit(HttpSession session) {
        long now = System.currentTimeMillis();
        long window = 60 * 1000L; // 1 minute

        @SuppressWarnings("unchecked")
        Deque<Long> queue = (Deque<Long>) session.getAttribute(RATE_LIMIT_ATTR);
        if (queue == null) {
            queue = new ArrayDeque<>();
            session.setAttribute(RATE_LIMIT_ATTR, queue);
        }

        while (!queue.isEmpty() && now - queue.peekFirst() > window) {
            queue.pollFirst();
        }

        if (queue.size() >= MAX_REQUESTS_PER_MINUTE) {
            logger.warn("Chat rate limit exceeded for session {}", session.getId());
            return false;
        }

        queue.addLast(now);
        return true;
    }
}
