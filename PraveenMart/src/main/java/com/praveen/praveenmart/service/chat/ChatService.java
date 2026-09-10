package com.praveen.praveenmart.service.chat;

import com.praveen.praveenmart.dao.DAOFactory;
import com.praveen.praveenmart.dao.ProductDAO;
import com.praveen.praveenmart.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service managing AI chatbot requests, caching, input validation, and guardrails.
 */
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private static final int MAX_INPUT_LENGTH = 500;
    private static final String SESSION_CACHE_ATTR = "CHAT_CACHE";

    private final ProductDAO productDAO;

    public ChatService() {
        this(DAOFactory.getProductDAO());
    }

    public ChatService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Processes a user chat message with caching, validation, and guardrails.
     *
     * @param userMessage raw message text
     * @param session     current user session for per-session caching
     * @return bot reply
     */
    public String askChatbot(String userMessage, HttpSession session) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            throw new ValidationException("Message cannot be empty.");
        }

        String trimmed = userMessage.trim();
        if (trimmed.length() > MAX_INPUT_LENGTH) {
            throw new ValidationException("Message exceeds maximum length of " + MAX_INPUT_LENGTH + " characters.");
        }

        String cacheKey = trimmed.toLowerCase(Locale.ROOT);

        // Guardrail: In-memory per-session caching for repeated identical questions
        if (session != null) {
            @SuppressWarnings("unchecked")
            Map<String, String> cache = (Map<String, String>) session.getAttribute(SESSION_CACHE_ATTR);
            if (cache != null && cache.containsKey(cacheKey)) {
                logger.debug("Returning cached chat response for: {}", cacheKey);
                return cache.get(cacheKey);
            }
        }

        String context = "Marketplace categories: Fashion & Style, Home & Kitchen, Electronics, Accessories. Total active listings: "
                + productDAO.countProducts();

        ChatProvider provider = ChatProviderFactory.getProvider();
        String reply = provider.getReply(trimmed, context);

        // Save in session cache
        if (session != null) {
            @SuppressWarnings("unchecked")
            Map<String, String> cache = (Map<String, String>) session.getAttribute(SESSION_CACHE_ATTR);
            if (cache == null) {
                cache = new ConcurrentHashMap<>();
                session.setAttribute(SESSION_CACHE_ATTR, cache);
            }
            cache.put(cacheKey, reply);
        }

        return reply;
    }
}
