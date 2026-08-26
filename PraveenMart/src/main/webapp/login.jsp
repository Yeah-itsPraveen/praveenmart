<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: var(--color-background);
            padding: 2rem 1.5rem;
        }

        .auth-wrapper {
            width: 100%;
            max-width: 440px;
        }

        .auth-card {
            background-color: var(--color-surface-container);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            padding: 2.5rem 2.2rem;
            box-shadow: var(--shadow-soft);
        }

        .auth-brand {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            font-family: var(--font-headline);
            font-size: 2rem;
            font-weight: 700;
            color: var(--color-primary);
            margin-bottom: 0.5rem;
            text-decoration: none;
        }

        .options-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 0.85rem;
            color: var(--color-on-surface-variant);
            margin-top: 1rem;
            margin-bottom: 1.5rem;
        }

        .remember-me {
            display: flex;
            align-items: center;
            gap: 0.4rem;
            cursor: pointer;
        }

        .remember-me input {
            accent-color: var(--color-primary);
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="auth-wrapper">
    <div class="auth-card">
        <a href="<%= request.getContextPath() %>/" class="auth-brand">
            <span>PraveenMart</span>
        </a>
        <p style="text-align: center; color: var(--color-on-surface-variant); font-size: 0.92rem; margin-bottom: 2rem;">
            Sign in to access your account & orders
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert-box alert-box-error">
                <span class="material-symbols-outlined">warning</span>
                <span><%= request.getAttribute("error") %></span>
            </div>
        <% } %>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert-box alert-box-success">
                <span class="material-symbols-outlined">check_circle</span>
                <span><%= request.getAttribute("success") %></span>
            </div>
        <% } %>

        <form action="login" method="post">
            <label class="form-label" for="role">Select Role</label>
            <div class="form-input-group">
                <div class="form-input-icon">
                    <span class="material-symbols-outlined">badge</span>
                </div>
                <select class="form-select-field" id="role" name="role" required>
                    <option value="CUSTOMER" selected>Customer</option>
                    <option value="SELLER">Seller</option>
                    <option value="ADMIN">Admin</option>
                </select>
                <div class="form-select-arrow">
                    <span class="material-symbols-outlined">expand_more</span>
                </div>
            </div>

            <label class="form-label" for="email">Email Address</label>
            <div class="form-input-group">
                <div class="form-input-icon">
                    <span class="material-symbols-outlined">mail</span>
                </div>
                <input type="email" class="form-input-field" id="email" name="email" placeholder="e.g. praveen@praveenmart.com" required autocomplete="email">
            </div>

            <label class="form-label" for="password">Password</label>
            <div class="form-input-group">
                <div class="form-input-icon">
                    <span class="material-symbols-outlined">lock</span>
                </div>
                <input type="password" class="form-input-field" id="password" name="password" placeholder="Enter your password" minlength="8" required>
            </div>

            <div class="options-row">
                <label class="remember-me">
                    <input type="checkbox" name="remember" checked>
                    <span>Remember me</span>
                </label>
                <a href="#" style="color: var(--color-on-surface-variant); font-size: 0.85rem;">Forgot Password?</a>
            </div>

            <button type="submit" class="btn btn-primary btn-pill" style="width: 100%; padding: 0.95rem; font-size: 1rem;">
                <span>Sign In</span>
                <span class="material-symbols-outlined">arrow_forward</span>
            </button>
        </form>

        <div style="margin-top: 2rem; text-align: center; font-size: 0.9rem; color: var(--color-on-surface-variant);">
            Don't have an account?
            <a href="register.jsp" style="font-weight: 700; text-decoration: underline;">Create Account</a>
        </div>
    </div>
</div>

</body>
</html>
