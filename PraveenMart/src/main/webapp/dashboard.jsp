<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.praveen.praveenmart.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Dashboard - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css">
    <style>
        .dashboard-container {
            max-width: 600px;
            margin: 3.5rem auto 5rem auto;
        }

        .profile-card {
            background-color: var(--color-surface-card);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            padding: 2.5rem;
            box-shadow: var(--shadow-soft);
            text-align: center;
        }

        .avatar-circle {
            width: 76px;
            height: 76px;
            border-radius: var(--radius-pill);
            background-color: var(--color-primary);
            color: #FFFFFF;
            font-family: var(--font-headline);
            font-size: 2rem;
            font-weight: 700;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1.25rem auto;
            box-shadow: 0 4px 14px rgba(74, 124, 89, 0.3);
        }

        .info-list {
            display: flex;
            flex-direction: column;
            gap: 0.75rem;
            margin: 2rem 0;
            text-align: left;
        }

        .info-item {
            background-color: var(--color-surface-container-low);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-md);
            padding: 1rem 1.25rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .info-key {
            font-size: 0.85rem;
            font-weight: 700;
            color: var(--color-on-surface-variant);
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .info-val {
            font-weight: 600;
            color: var(--color-on-surface);
        }
    </style>
</head>
<body>

    <%@ include file="/includes/header.jspf" %>

    <main class="container">
        <div class="dashboard-container">
            <% if (user != null) { %>
                <div class="profile-card">
                    <div class="avatar-circle">
                        <%= (user.getName() != null && !user.getName().isBlank()) ? user.getName().substring(0, 1).toUpperCase() : "U" %>
                    </div>

                    <h1 class="font-headline" style="font-size: 1.8rem; margin-bottom: 0.35rem;">
                        Welcome back, <%= user.getName() %>!
                    </h1>
                    <span class="badge-tag badge-primary"><%= user.getRole() %> Account</span>

                    <div class="info-list">
                        <div class="info-item">
                            <span class="info-key">Full Name</span>
                            <span class="info-val"><%= user.getName() %></span>
                        </div>
                        <div class="info-item">
                            <span class="info-key">Email</span>
                            <span class="info-val"><%= user.getEmail() %></span>
                        </div>
                        <div class="info-item">
                            <span class="info-key">Role</span>
                            <span class="info-val"><%= user.getRole() %></span>
                        </div>
                    </div>

                    <div style="display: flex; flex-direction: column; gap: 0.75rem;">
                        <% if ("ADMIN".equalsIgnoreCase(user.getRole())) { %>
                            <a href="<%= request.getContextPath() %>/admin/dashboard" class="btn btn-primary btn-pill" style="width: 100%; background: #b06000;">
                                <span class="material-symbols-outlined">admin_panel_settings</span>
                                <span>Open Admin Control Center</span>
                            </a>
                        <% } %>

                        <% if ("SELLER".equalsIgnoreCase(user.getRole()) || "ADMIN".equalsIgnoreCase(user.getRole())) { %>
                            <a href="<%= request.getContextPath() %>/seller/dashboard" class="btn btn-secondary btn-pill" style="width: 100%;">
                                <span class="material-symbols-outlined">inventory_2</span>
                                <span>Open Seller Hub & Orders</span>
                            </a>
                        <% } %>

                        <a href="<%= request.getContextPath() %>/orders" class="btn btn-secondary btn-pill" style="width: 100%;">
                            <span class="material-symbols-outlined">receipt_long</span>
                            <span>My Order History</span>
                        </a>

                        <a href="<%= request.getContextPath() %>/products" class="btn btn-primary btn-pill" style="width: 100%;">
                            <span class="material-symbols-outlined">shopping_bag</span>
                            <span>Explore Marketplace</span>
                        </a>

                        <a href="<%= request.getContextPath() %>/logout" class="btn btn-outlined btn-pill" style="width: 100%;">
                            <span class="material-symbols-outlined">logout</span>
                            <span>Sign Out</span>
                        </a>
                    </div>
                </div>
            <% } else { %>
                <div class="profile-card">
                    <span class="material-symbols-outlined" style="font-size: 3.5rem; color: var(--color-outline); margin-bottom: 1rem;">lock</span>
                    <h2 class="font-headline" style="margin-bottom: 0.5rem;">Access Required</h2>
                    <p style="color: var(--color-on-surface-variant); margin-bottom: 2rem;">Please sign in to view your profile dashboard.</p>
                    <a href="<%= request.getContextPath() %>/login.jsp" class="btn btn-primary btn-pill">Sign In</a>
                </div>
            <% } %>
        </div>
    </main>

    <%@ include file="/includes/footer.jspf" %>

</body>
</html>
