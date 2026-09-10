<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.praveen.praveenmart.model.WishlistItem" %>
<%@ page import="com.praveen.praveenmart.model.Product" %>
<%
    List<WishlistItem> wishlistItems = (List<WishlistItem>) request.getAttribute("wishlistItems");
    String msgSuccess = (String) session.getAttribute("msgSuccess");
    String msgError = (String) session.getAttribute("msgError");
    session.removeAttribute("msgSuccess");
    session.removeAttribute("msgError");

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Wishlist - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css">
    <style>
        .wishlist-wrapper {
            padding: 3rem 0 5rem;
            min-height: 70vh;
        }

        .wishlist-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }

        .wishlist-card {
            background-color: var(--color-surface-card);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            overflow: hidden;
            box-shadow: var(--shadow-soft);
            display: flex;
            flex-direction: column;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .wishlist-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 28px rgba(0,0,0,0.08);
        }

        .wishlist-img-wrapper {
            position: relative;
            width: 100%;
            height: 220px;
            background-color: var(--color-surface-variant);
            overflow: hidden;
        }

        .wishlist-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s ease;
        }

        .wishlist-card:hover .wishlist-img {
            transform: scale(1.04);
        }

        .wishlist-body {
            padding: 1.5rem;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }

        .wishlist-title {
            font-size: 1.15rem;
            font-weight: 700;
            color: var(--color-on-surface);
            margin-bottom: 0.5rem;
            text-decoration: none;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .wishlist-price {
            font-size: 1.25rem;
            font-weight: 800;
            color: var(--color-primary);
            margin-bottom: 1.2rem;
        }

        .wishlist-actions {
            display: flex;
            gap: 0.75rem;
            margin-top: auto;
        }

        .empty-wishlist {
            text-align: center;
            padding: 5rem 1rem;
            background-color: var(--color-surface-card);
            border-radius: var(--radius-xl);
            border: 1px dashed var(--color-outline-variant);
            margin-top: 2rem;
        }
    </style>
</head>
<body>

    <%@ include file="/includes/header.jspf" %>

    <main class="wishlist-wrapper">
        <div class="container">
            <div style="display: flex; justify-content: space-between; align-items: baseline;">
                <div>
                    <h1 class="font-headline" style="font-size: 2.2rem; font-weight: 800;">My Wishlist</h1>
                    <p style="color: var(--color-on-surface-variant); margin-top: 0.25rem;">
                        <%= (wishlistItems != null ? wishlistItems.size() : 0) %> item(s) saved for later
                    </p>
                </div>
                <a href="<%= request.getContextPath() %>/products" class="btn btn-outlined btn-pill">
                    <span class="material-symbols-outlined" style="font-size: 1.1rem;">arrow_back</span>
                    <span>Continue Shopping</span>
                </a>
            </div>

            <% if (msgSuccess != null) { %>
                <div class="alert alert-success" style="margin-top: 1.5rem; padding: 1rem; background-color: #e8f5e9; color: #1b5e20; border-radius: var(--radius-md);">
                    <%= msgSuccess %>
                </div>
            <% } %>

            <% if (msgError != null) { %>
                <div class="alert alert-error" style="margin-top: 1.5rem; padding: 1rem; background-color: #ffebee; color: #b71c1c; border-radius: var(--radius-md);">
                    <%= msgError %>
                </div>
            <% } %>

            <% if (wishlistItems == null || wishlistItems.isEmpty()) { %>
                <div class="empty-wishlist">
                    <span class="material-symbols-outlined" style="font-size: 4rem; color: var(--color-outline);">favorite_border</span>
                    <h3 style="margin-top: 1rem; font-size: 1.3rem; font-weight: 700;">Your wishlist is empty</h3>
                    <p style="color: var(--color-on-surface-variant); margin-top: 0.5rem; max-width: 400px; margin-left: auto; margin-right: auto;">
                        Explore our marketplace catalog and click the heart icon on any product to save it here for later.
                    </p>
                    <a href="<%= request.getContextPath() %>/products" class="btn btn-primary btn-pill" style="margin-top: 1.5rem;">
                        Browse Products
                    </a>
                </div>
            <% } else { %>
                <div class="wishlist-grid">
                    <% for (WishlistItem item : wishlistItems) {
                        Product p = item.getProduct();
                        if (p == null) continue;
                    %>
                        <div class="wishlist-card">
                            <div class="wishlist-img-wrapper">
                                <% if (p.getImageUrl() != null && !p.getImageUrl().isBlank()) { %>
                                    <img src="<%= p.getImageUrl() %>"
                                         alt="<%= p.getName() %>"
                                         class="wishlist-img"
                                         onerror="this.src='https://images.unsplash.com/photo-1544816155-12df9643f363?w=600&auto=format&fit=crop&q=80'"
                                         loading="lazy">
                                <% } else { %>
                                    <img src="https://images.unsplash.com/photo-1544816155-12df9643f363?w=600&auto=format&fit=crop&q=80"
                                         alt="Product Placeholder"
                                         class="wishlist-img"
                                         loading="lazy">
                                <% } %>
                            </div>
                            <div class="wishlist-body">
                                <span class="badge" style="align-self: flex-start; margin-bottom: 0.5rem; font-size: 0.75rem; background: #e0f2fe; color: #0369a1; padding: 0.2rem 0.6rem; border-radius: 999px;">
                                    <%= p.getCategory() %>
                                </span>
                                <a href="<%= request.getContextPath() %>/product-details?id=<%= p.getId() %>" class="wishlist-title">
                                    <%= p.getName() %>
                                </a>
                                <div class="wishlist-price">
                                    <%= currencyFormat.format(p.getPrice()) %>
                                </div>
                                <div style="font-size: 0.85rem; color: <%= p.getStockQty() > 0 ? "#15803d" : "#b91c1c" %>; margin-bottom: 1rem;">
                                    <%= p.getStockQty() > 0 ? "In Stock (" + p.getStockQty() + " available)" : "Out of Stock" %>
                                </div>
                                <div class="wishlist-actions">
                                    <form action="<%= request.getContextPath() %>/wishlist/move-to-cart" method="post" style="flex: 1;">
                                        <input type="hidden" name="productId" value="<%= p.getId() %>">
                                        <button type="submit" class="btn btn-primary btn-pill" style="width: 100%;" <%= p.getStockQty() <= 0 ? "disabled" : "" %>>
                                            <span class="material-symbols-outlined" style="font-size: 1.1rem;">shopping_bag</span>
                                            <span>Move to Cart</span>
                                        </button>
                                    </form>
                                    <form action="<%= request.getContextPath() %>/wishlist/remove" method="post">
                                        <input type="hidden" name="productId" value="<%= p.getId() %>">
                                        <button type="submit" class="btn btn-outlined btn-pill" title="Remove from Wishlist" style="padding: 0.6rem;">
                                            <span class="material-symbols-outlined" style="color: #ef4444; font-size: 1.2rem;">delete</span>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    </main>

    <%@ include file="/includes/footer.jspf" %>

</body>
</html>
