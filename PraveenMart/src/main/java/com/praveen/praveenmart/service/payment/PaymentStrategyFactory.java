package com.praveen.praveenmart.service.payment;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory and registry for PaymentStrategy implementations.
 */
public class PaymentStrategyFactory {

    private static final Map<String, PaymentStrategy> strategies = new HashMap<>();

    static {
        registerStrategy(new CreditCardPaymentStrategy());
        registerStrategy(new UPIPaymentStrategy());
        registerStrategy(new CashOnDeliveryPaymentStrategy());
    }

    public static void registerStrategy(PaymentStrategy strategy) {
        strategies.put(strategy.getMethodName().toUpperCase(), strategy);
    }

    public static PaymentStrategy getStrategy(String method) {
        if (method == null || method.isBlank()) {
            return strategies.get("COD");
        }
        String normalized = method.trim().toUpperCase();
        if (normalized.contains("CARD") || normalized.contains("CREDIT") || normalized.contains("DEBIT")) {
            return strategies.get("CREDIT_CARD");
        }
        if (normalized.contains("UPI") || normalized.contains("GPAY") || normalized.contains("PHONEPE")) {
            return strategies.get("UPI");
        }
        return strategies.getOrDefault(normalized, strategies.get("COD"));
    }
}
