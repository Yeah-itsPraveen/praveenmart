package com.praveen.praveenmart.service.payment;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Strategy pattern interface for swappable payment/mock channels (Section 12).
 */
public interface PaymentStrategy {

    PaymentResult processPayment(Long orderId, BigDecimal amount, Map<String, String> paymentDetails);

    String getMethodName();
}
