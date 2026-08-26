<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.praveen.praveenmart.model.CartItem" %>
<%@ page import="com.praveen.praveenmart.model.Product" %>
<%@ page import="com.praveen.praveenmart.model.User" %>
<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    BigDecimal subtotal = (BigDecimal) request.getAttribute("subtotal");
    BigDecimal shipping = (BigDecimal) request.getAttribute("shipping");
    BigDecimal grandTotal = (BigDecimal) request.getAttribute("grandTotal");
    String error = (String) request.getAttribute("error");

    if (subtotal == null) subtotal = BigDecimal.ZERO;
    if (shipping == null) shipping = BigDecimal.ZERO;
    if (grandTotal == null) grandTotal = BigDecimal.ZERO;

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css">
    <style>
        .checkout-wrapper {
            padding: 3rem 0 5rem;
        }

        .checkout-layout {
            display: grid;
            grid-template-columns: 1fr 380px;
            gap: 2.5rem;
            align-items: start;
        }

        @media (max-width: 900px) {
            .checkout-layout {
                grid-template-columns: 1fr;
            }
        }

        .checkout-section {
            background-color: var(--color-surface-card);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: var(--shadow-soft);
        }

        .checkout-section-title {
            font-size: 1.3rem;
            font-weight: 700;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: var(--color-on-surface);
        }

        .payment-method-card {
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-md);
            padding: 1rem 1.25rem;
            margin-bottom: 0.8rem;
            cursor: pointer;
            transition: all 0.2s ease;
            display: flex;
            align-items: center;
            gap: 0.8rem;
            background-color: var(--color-surface-container-low);
        }

        .payment-method-card:hover {
            border-color: var(--color-primary);
        }

        .payment-method-card input[type="radio"]:checked + .payment-method-label {
            color: var(--color-primary);
            font-weight: 700;
        }

        .payment-method-label {
            flex: 1;
            font-size: 0.95rem;
            cursor: pointer;
        }

        .summary-card {
            background-color: var(--color-surface-container-low);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            padding: 1.8rem;
            box-shadow: var(--shadow-soft);
        }
    </style>
</head>
<body>

<%@ include file="/includes/header.jspf" %>

<main class="container checkout-wrapper">
    <h1 style="font-size: 2.2rem; font-weight: 700; margin-bottom: 2rem;">Secure Checkout</h1>

    <% if (error != null) { %>
        <div class="alert-box alert-box-error">
            <span class="material-symbols-outlined">warning</span>
            <span><%= error %></span>
        </div>
    <% } %>

    <form action="<%= request.getContextPath() %>/checkout" method="post" id="checkout-form">
        <div class="checkout-layout">

            <div>

                <div class="checkout-section">
                    <div class="checkout-section-title">
                        <span class="material-symbols-outlined" style="color: var(--color-primary);">local_shipping</span>
                        <span>1. Shipping Address</span>
                    </div>

                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; margin-bottom: 1rem;">
                        <div>
                            <label class="form-label" for="fullName">Recipient Full Name</label>
                            <input type="text" id="fullName" name="fullName" class="form-input-field" style="width: 100%; background: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 0.8rem;" value="<%= sessionUser != null ? sessionUser.getName() : "" %>" required>
                        </div>
                        <div>
                            <label class="form-label" for="phone">Contact Phone Number</label>
                            <input type="tel" id="phone" name="phone" class="form-input-field" style="width: 100%; background: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 0.8rem;" placeholder="+91 98765 43210" required>
                        </div>
                    </div>

                    <div style="margin-bottom: 1rem;">
                        <label class="form-label" for="street">Street Address & Flat / House No.</label>
                        <input type="text" id="street" name="street" class="form-input-field" style="width: 100%; background: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 0.8rem;" placeholder="e.g. 42, Green Avenue, Anna Nagar" required>
                    </div>

                    <div style="display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 1rem;">
                        <div>
                            <label class="form-label" for="city">City</label>
                            <input type="text" id="city" name="city" class="form-input-field" style="width: 100%; background: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 0.8rem;" placeholder="Chennai" required>
                        </div>
                        <div>
                            <label class="form-label" for="state">State</label>
                            <input type="text" id="state" name="state" class="form-input-field" style="width: 100%; background: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 0.8rem;" placeholder="Tamil Nadu" required>
                        </div>
                        <div>
                            <label class="form-label" for="pincode">PIN Code</label>
                            <input type="text" id="pincode" name="pincode" class="form-input-field" style="width: 100%; background: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 0.8rem;" placeholder="600025" required>
                        </div>
                    </div>
                </div>

                <div class="checkout-section">
                    <div class="checkout-section-title">
                        <span class="material-symbols-outlined" style="color: var(--color-primary);">credit_card</span>
                        <span>2. Mock Payment Confirmation</span>
                    </div>

                    <div class="payment-method-card">
                        <input type="radio" id="pay-card" name="paymentMethod" value="CARD" checked>
                        <label for="pay-card" class="payment-method-label">
                            <strong>Mock Credit / Debit Card</strong> (Instant simulated confirmation)
                        </label>
                    </div>

                    <div class="payment-method-card">
                        <input type="radio" id="pay-upi" name="paymentMethod" value="UPI">
                        <label for="pay-upi" class="payment-method-label">
                            <strong>Mock UPI / QR</strong> (GPay / PhonePe / Paytm mock)
                        </label>
                    </div>

                    <div class="payment-method-card">
                        <input type="radio" id="pay-cod" name="paymentMethod" value="COD">
                        <label for="pay-cod" class="payment-method-label">
                            <strong>Cash on Delivery (COD)</strong> (Pay on physical delivery)
                        </label>
                    </div>

                    <div style="margin-top: 1rem; font-size: 0.85rem; color: var(--color-on-surface-variant); background: var(--color-surface-container); padding: 0.75rem 1rem; border-radius: var(--radius-md);">
                        <span class="material-symbols-outlined" style="font-size: 1rem; vertical-align: middle;">info</span>
                        <em>Note: Per project specification, this is a simulated checkout test. No real money will be charged.</em>
                    </div>
                </div>
            </div>

            <div class="summary-card">
                <h3 style="font-size: 1.3rem; margin-bottom: 1.25rem; font-weight: 700;">Order Items (<%= cartItems != null ? cartItems.size() : 0 %>)</h3>

                <div style="max-height: 260px; overflow-y: auto; margin-bottom: 1.5rem; padding-right: 0.5rem;">
                    <% if (cartItems != null) { %>
                        <% for (CartItem item : cartItems) {
                            Product p = item.getProduct();
                        %>
                            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.8rem; font-size: 0.9rem;">
                                <div style="flex: 1; padding-right: 0.5rem;">
                                    <div style="font-weight: 600; color: var(--color-on-surface);"><%= p != null ? p.getName() : "Item" %></div>
                                    <div style="font-size: 0.8rem; color: var(--color-on-surface-variant);">Qty: <%= item.getQuantity() %></div>
                                </div>
                                <div style="font-weight: 700; color: var(--color-neutral-dark);">
                                    <%= currencyFormat.format(item.getItemTotal()) %>
                                </div>
                            </div>
                        <% } %>
                    <% } %>
                </div>

                <div style="border-top: 1px solid var(--color-outline-variant); padding-top: 1rem; margin-top: 1rem;">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.6rem; font-size: 0.95rem; color: var(--color-on-surface-variant);">
                        <span>Subtotal</span>
                        <span style="font-weight: 600;"><%= currencyFormat.format(subtotal) %></span>
                    </div>

                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.6rem; font-size: 0.95rem; color: var(--color-on-surface-variant);">
                        <span>Shipping</span>
                        <span style="font-weight: 600;">
                            <% if (shipping.compareTo(BigDecimal.ZERO) == 0) { %>
                                <span style="color: var(--color-success); font-weight: 700;">FREE</span>
                            <% } else { %>
                                <%= currencyFormat.format(shipping) %>
                            <% } %>
                        </span>
                    </div>

                    <div style="display: flex; justify-content: space-between; margin-top: 1rem; padding-top: 1rem; border-top: 1px solid var(--color-outline-variant); font-size: 1.3rem; font-weight: 800; color: var(--color-neutral-dark);">
                        <span>Total Payable</span>
                        <span><%= currencyFormat.format(grandTotal) %></span>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary btn-pill" style="width: 100%; padding: 1rem; font-size: 1.05rem; margin-top: 1.8rem;">
                    <span class="material-symbols-outlined">verified_user</span>
                    <span>Confirm & Place Order</span>
                </button>
            </div>
        </div>
    </form>
</main>

<%@ include file="/includes/footer.jspf" %>

</body>
</html>
