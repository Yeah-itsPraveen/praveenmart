<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css?v=1.1">
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

        .password-toggle-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            background: transparent !important;
            background-color: transparent !important;
            border: none !important;
            border-width: 0 !important;
            outline: none !important;
            box-shadow: none !important;
            padding: 0 0.95rem;
            color: var(--color-on-surface-variant);
            cursor: pointer;
            transition: color 0.18s ease, transform 0.1s ease;
            user-select: none;
            flex-shrink: 0;
            -webkit-appearance: none !important;
            appearance: none !important;
        }

        .password-toggle-btn:hover {
            color: var(--color-primary);
            background: transparent !important;
            border: none !important;
        }

        .password-toggle-btn:focus,
        .password-toggle-btn:active {
            background: transparent !important;
            border: none !important;
            outline: none !important;
            box-shadow: none !important;
            transform: scale(0.92);
        }

        .password-toggle-btn .material-symbols-outlined {
            font-size: 1.28rem;
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
                <input type="email" class="form-input-field" id="email" name="email" placeholder="e.g. user@example.com" required autocomplete="email">
            </div>

            <label class="form-label" for="password">Password</label>
            <div class="form-input-group">
                <div class="form-input-icon">
                    <span class="material-symbols-outlined">lock</span>
                </div>
                <input type="password" class="form-input-field" id="password" name="password" placeholder="Enter your password" minlength="8" required autocomplete="current-password">
                <button type="button" class="password-toggle-btn" id="togglePasswordBtn" title="Show password" aria-label="Show password" tabindex="-1" style="background: transparent !important; background-color: transparent !important; border: none !important; border-width: 0 !important; outline: none !important; box-shadow: none !important; padding: 0 0.95rem; cursor: pointer; display: inline-flex; align-items: center; justify-content: center; color: var(--color-on-surface-variant); -webkit-appearance: none !important; appearance: none !important;">
                    <span class="material-symbols-outlined" id="togglePasswordIcon">visibility</span>
                </button>
            </div>

            <div class="options-row">
                <label class="remember-me">
                    <input type="checkbox" name="remember" checked>
                    <span>Remember me</span>
                </label>
                <label style="display: flex; align-items: center; gap: 0.35rem; cursor: pointer; font-size: 0.85rem; color: var(--color-on-surface-variant); user-select: none;">
                    <input type="checkbox" id="showPasswordCheckbox" style="accent-color: var(--color-primary); cursor: pointer;">
                    <span>Show password</span>
                </label>
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

<script>
    (function() {
        const pwdInput = document.getElementById('password');
        const toggleBtn = document.getElementById('togglePasswordBtn');
        const toggleIcon = document.getElementById('togglePasswordIcon');
        const showCb = document.getElementById('showPasswordCheckbox');

        if (!pwdInput) return;

        function setVisibility(show) {
            pwdInput.type = show ? 'text' : 'password';
            if (toggleIcon) {
                toggleIcon.textContent = show ? 'visibility_off' : 'visibility';
            }
            if (toggleBtn) {
                toggleBtn.setAttribute('title', show ? 'Hide password' : 'Show password');
                toggleBtn.setAttribute('aria-label', show ? 'Hide password' : 'Show password');
            }
            if (showCb && showCb.checked !== show) {
                showCb.checked = show;
            }
        }

        if (toggleBtn) {
            toggleBtn.addEventListener('click', function(e) {
                e.preventDefault();
                const willShow = pwdInput.type === 'password';
                setVisibility(willShow);
                pwdInput.focus();
            });
        }

        if (showCb) {
            showCb.addEventListener('change', function() {
                setVisibility(showCb.checked);
                pwdInput.focus();
            });
        }
    })();
</script>

</body>
</html>
