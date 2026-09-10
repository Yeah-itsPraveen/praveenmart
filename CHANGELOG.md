# Changelog

All notable changes to the PraveenMart project are documented in this file in accordance with Semantic Versioning.

## [v1.1.0] - 2026-09-28
- feat: integrate AI Chatbot with floating UI widget, Gemini & Mock ChatProviders, rate-limiting, and question caching (Phase 3).
- feat: implement Wishlist (Save-for-Later) feature (O1) with full persistence, migration script, and move-to-cart workflow.
- feat: provide comprehensive versioned REST API endpoints under `/api/v1/...` for products, cart, orders, wishlist, reviews, and health check.
- feat: implement Strategy pattern for swappable payment mock channels (Credit Card, UPI, Cash on Delivery).
- feat: implement Factory pattern with `DAOFactory`, `ChatProviderFactory`, and `PaymentStrategyFactory`.
- feat: implement Builder pattern with `OrderSummaryDTO.Builder`, `ProductResponseDTO.Builder`, and `UserResponseDTO.Builder`.

## [v1.0.0] - 2026-09-21
- feat: full multi-seller marketplace with Seller Hub (listing CRUD, inventory management, revenue metrics).
- feat: admin dashboard with user moderation, platform order metrics, and listing moderation.
- feat: order status progression workflow (PENDING -> CONFIRMED -> SHIPPED -> DELIVERED -> CANCELLED).
- feat: product reviews and star ratings system.
- test: complete unit and DAO test suite covering core service business rules and database queries.
- sec: enforce strict PreparedStatement parameterization, BCrypt hashing, and session fixation prevention.

## [v0.2.0] - 2026-08-23
- feat: dynamic category filtering and keyword search with case-insensitive LIKE matching.
- feat: seller incoming orders view and revenue calculation.
- style: responsive modern UI with Material design tokens, mobile navigation, and empty cart states.

## [v0.1.0] - 2026-08-10
- feat: core MVP user journey (browse catalog, add to cart, mock checkout, order placement).
- feat: user authentication (Buyer/Seller registration, login, bcrypt password hashing, session timeout).
- feat: database schema initialization with H2 and HikariCP connection pool.
