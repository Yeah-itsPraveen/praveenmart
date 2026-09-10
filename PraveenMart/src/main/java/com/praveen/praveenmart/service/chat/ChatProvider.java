package com.praveen.praveenmart.service.chat;

/**
 * Common interface for AI chat providers as mandated by Section 17 of the specification.
 */
public interface ChatProvider {

    /**
     * Generates a conversational reply for a user message within the marketplace domain.
     *
     * @param userMessage the message from the buyer/seller
     * @param context     marketplace context (e.g., categories, active catalog)
     * @return reply string
     */
    String getReply(String userMessage, String context);
}
