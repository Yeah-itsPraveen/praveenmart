package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.ProductDAO;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.service.chat.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private HttpSession session;

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatService = new ChatService(productDAO);
    }

    @Test
    @DisplayName("Should reject empty or whitespace-only messages")
    void testEmptyMessageRejection() {
        assertThrows(ValidationException.class, () -> chatService.askChatbot("", session));
        assertThrows(ValidationException.class, () -> chatService.askChatbot("   ", session));
        assertThrows(ValidationException.class, () -> chatService.askChatbot(null, session));
    }

    @Test
    @DisplayName("Should reject messages exceeding 500 characters")
    void testMaxLengthRejection() {
        String longMessage = "a".repeat(501);
        assertThrows(ValidationException.class, () -> chatService.askChatbot(longMessage, session));
    }

    @Test
    @DisplayName("Should provide domain FAQ responses for common questions")
    void testDomainFaqResponses() {
        when(productDAO.countProducts()).thenReturn(20);

        String reply1 = chatService.askChatbot("How long does shipping take?", null);
        assertNotNull(reply1);
        assertTrue(reply1.toLowerCase().contains("delivery") || reply1.toLowerCase().contains("shipping"));

        String reply2 = chatService.askChatbot("What is your return policy?", null);
        assertNotNull(reply2);
        assertTrue(reply2.toLowerCase().contains("return"));

        String reply3 = chatService.askChatbot("How can I become a seller?", null);
        assertNotNull(reply3);
        assertTrue(reply3.toLowerCase().contains("seller"));
    }

    @Test
    @DisplayName("Should return cached response for repeated identical question within session")
    void testSessionCaching() {
        Map<String, String> cache = new HashMap<>();
        cache.put("how long does shipping take?", "CACHED_ANSWER");

        when(session.getAttribute("CHAT_CACHE")).thenReturn(cache);

        String reply = chatService.askChatbot("How long does shipping take?", session);
        assertEquals("CACHED_ANSWER", reply);
        verify(productDAO, never()).countProducts();
    }
}
