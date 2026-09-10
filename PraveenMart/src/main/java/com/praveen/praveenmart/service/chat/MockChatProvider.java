package com.praveen.praveenmart.service.chat;

import java.util.Locale;

/**
 * MockChatProvider implementing domain FAQ knowledge base without network overhead.
 * Minimum functional scope covers 10+ FAQ-style domain questions.
 */
public class MockChatProvider implements ChatProvider {

    @Override
    public String getReply(String userMessage, String context) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Hello! How can I assist you with your shopping on PraveenMart today?";
        }

        String lower = userMessage.trim().toLowerCase(Locale.ROOT);

        // 1. Shipping & Delivery
        if (lower.contains("shipping") || lower.contains("delivery") || lower.contains("how long") || lower.contains("dispatch")) {
            return "Standard delivery takes 2–4 business days for metro locations and 3–6 business days nationwide. Shipping is free on all orders over ₹2,000!";
        }

        // 2. Returns & Refunds
        if (lower.contains("return") || lower.contains("refund") || lower.contains("exchange") || lower.contains("replacement")) {
            return "PraveenMart provides a hassle-free 7-day return policy. If you receive a damaged or incorrect item, you can initiate a return from your Orders page, and refunds are processed within 3–5 business days.";
        }

        // 3. Tracking orders
        if (lower.contains("track") || lower.contains("status") || lower.contains("where is my order")) {
            return "You can check your live order status by navigating to 'My Orders' in your account menu. Statuses progress through PENDING → CONFIRMED → SHIPPED → DELIVERED.";
        }

        // 4. Payment methods
        if (lower.contains("payment") || lower.contains("pay") || lower.contains("upi") || lower.contains("card") || lower.contains("cod") || lower.contains("cash on delivery")) {
            return "We accept all major Credit/Debit Cards, secure UPI (Google Pay, PhonePe, Paytm), and Cash on Delivery (COD) for your convenience.";
        }

        // 5. Becoming a seller / Selling
        if (lower.contains("seller") || lower.contains("sell") || lower.contains("merchant") || lower.contains("list product")) {
            return "Anyone can start selling on PraveenMart! Select the 'Seller' role when registering an account, then open the 'Seller Hub' to add and manage your product listings with photos and pricing.";
        }

        // 6. Cancellation
        if (lower.contains("cancel") || lower.contains("cancellation")) {
            return "Orders can be cancelled before they reach 'SHIPPED' status. Visit your 'My Orders' section to request a cancellation.";
        }

        // 7. Product authenticity & warranty
        if (lower.contains("authentic") || lower.contains("genuine") || lower.contains("warranty") || lower.contains("quality")) {
            return "All products on PraveenMart are verified and sourced from authorized independent creators and merchants. Electronics come with a standard manufacturer or seller warranty.";
        }

        // 8. Wishlist & Save for later
        if (lower.contains("wishlist") || lower.contains("save for later") || lower.contains("favorite")) {
            return "You can click the heart icon on any product card to save it to your Wishlist. You can review saved items anytime and move them directly to your cart!";
        }

        // 9. Categories & Products
        if (lower.contains("category") || lower.contains("categories") || lower.contains("products") || lower.contains("items") || lower.contains("fashion") || lower.contains("electronics")) {
            return "PraveenMart features curated collections in Fashion & Style, Electronics, Home & Kitchen, and Accessories. Use the top navigation or search bar to discover top items!";
        }

        // 10. Customer Support & Contact
        if (lower.contains("contact") || lower.contains("support") || lower.contains("help") || lower.contains("email") || lower.contains("phone")) {
            return "Our customer support team is here for you! Email us at support@praveenmart.com or reach out via our help center. We respond within 24 hours.";
        }

        // 11. Discount / coupon / sale
        if (lower.contains("discount") || lower.contains("coupon") || lower.contains("offer") || lower.contains("promo") || lower.contains("sale")) {
            return "Enjoy exciting seasonal promotions across all categories! Free delivery applies automatically on carts over ₹2,000.";
        }

        // 12. Greetings
        if (lower.startsWith("hi") || lower.startsWith("hello") || lower.startsWith("hey") || lower.equals("greetings")) {
            return "Hello and welcome to PraveenMart! I'm your AI Shopping Assistant. Ask me about shipping, tracking, return policies, payment methods, or finding products!";
        }

        // Default out-of-scope guidance
        return "I am PraveenMart's AI Shopping Assistant. I can help with product inquiries, shipping timelines, order tracking, returns, payment methods, and seller onboarding. How can I assist you today?";
    }
}
