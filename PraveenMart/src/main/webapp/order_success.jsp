<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.praveen.praveenmart.model.Order" %>
<%
    Order order = (Order) request.getAttribute("order");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css">
    <style>
        .success-wrapper {
            padding: 4rem 1.5rem 6rem;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .success-card {
            background-color: var(--color-surface-card);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            padding: 3.5rem 2.5rem;
            max-width: 540px;
            width: 100%;
            text-align: center;
            box-shadow: var(--shadow-md);
        }

        .success-icon-box {
            width: 80px;
            height: 80px;
            background-color: var(--color-primary-light);
            color: var(--color-primary);
            border-radius: var(--radius-pill);
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1.5rem;
        }
    </style>
</head>
<body>

<%@ include file="/includes/header.jspf" %>

<div class="success-wrapper">
    <div class="success-card">
        <div class="success-icon-box">
            <span class="material-symbols-outlined" style="font-size: 3rem;">check_circle</span>
        </div>

        <h1 style="font-size: 2.2rem; font-weight: 700; margin-bottom: 0.5rem; color: var(--color-on-surface);">Order Placed Successfully!</h1>
        <p style="color: var(--color-on-surface-variant); font-size: 1rem; margin-bottom: 2rem;">
            Thank you for supporting mindful commerce. We have received your order and are getting it ready for dispatch.
        </p>

        <% if (order != null) { %>
            <div style="background-color: var(--color-surface-container-low); border: 1px solid var(--color-outline-variant); border-radius: var(--radius-md); padding: 1.25rem; margin-bottom: 2rem; text-align: left;">
                <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem; font-size: 0.95rem;">
                    <span style="color: var(--color-on-surface-variant);">Order Reference:</span>
                    <span style="font-weight: 700; color: var(--color-primary);">#ORD-<%= order.getId() %></span>
                </div>
                <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem; font-size: 0.95rem;">
                    <span style="color: var(--color-on-surface-variant);">Total Paid:</span>
                    <span style="font-weight: 700; color: var(--color-neutral-dark);"><%= currencyFormat.format(order.getTotalAmount()) %></span>
                </div>
                <div style="display: flex; justify-content: space-between; font-size: 0.95rem;">
                    <span style="color: var(--color-on-surface-variant);">Status:</span>
                    <span class="badge-tag badge-in-stock"><%= order.getStatus() %></span>
                </div>
            </div>
        <% } %>

        <div style="display: flex; gap: 1rem; justify-content: center; flex-wrap: wrap;">
            <a href="<%= request.getContextPath() %>/orders" class="btn btn-primary btn-pill" style="padding: 0.8rem 1.8rem;">
                <span class="material-symbols-outlined">receipt_long</span>
                <span>View Order History</span>
            </a>
            <a href="<%= request.getContextPath() %>/products" class="btn btn-secondary btn-pill" style="padding: 0.8rem 1.8rem;">
                <span>Continue Shopping</span>
            </a>
        </div>
    </div>
</div>

<%@ include file="/includes/footer.jspf" %>

</body>
</html>
