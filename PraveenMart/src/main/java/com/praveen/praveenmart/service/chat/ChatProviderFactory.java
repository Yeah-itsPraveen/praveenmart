package com.praveen.praveenmart.service.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for ChatProvider implementations based on configuration flag (Section 17).
 */
public class ChatProviderFactory {

    private static final Logger logger = LoggerFactory.getLogger(ChatProviderFactory.class);
    private static final ChatProvider mockProvider = new MockChatProvider();
    private static final ChatProvider geminiProvider = new GeminiChatProvider();

    private ChatProviderFactory() {
    }

    public static ChatProvider getProvider() {
        String config = System.getProperty("ai.chatbot.provider", System.getenv("AI_CHATBOT_PROVIDER"));
        if (config != null && "gemini".equalsIgnoreCase(config.trim())) {
            logger.debug("Using GeminiChatProvider");
            return geminiProvider;
        }
        return mockProvider;
    }
}
