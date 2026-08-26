<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error - PraveenMart</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/theme.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: var(--color-background);
            text-align: center;
            padding: 2rem;
        }
        .error-card {
            background-color: var(--color-surface-card);
            border: 1px solid var(--color-outline-variant);
            border-radius: var(--radius-xl);
            padding: 3.5rem 2.5rem;
            max-width: 500px;
            box-shadow: var(--shadow-soft);
        }
    </style>
</head>
<body>
    <div class="error-card">
        <span class="material-symbols-outlined" style="font-size: 4rem; color: var(--color-danger); margin-bottom: 1rem;">error</span>
        <h1 style="font-size: 2rem; font-weight: 700; margin-bottom: 0.5rem;">500 - Internal Server Error</h1>
        <p style="color: var(--color-on-surface-variant); margin-bottom: 2rem;">
            Something unexpected occurred on our end. Please try again later or return to the marketplace.
        </p>
        <a href="<%= request.getContextPath() %>/products" class="btn btn-primary btn-pill">
            <span class="material-symbols-outlined">home</span>
            <span>Return to Storefront</span>
        </a>
    </div>
</body>
</html>
