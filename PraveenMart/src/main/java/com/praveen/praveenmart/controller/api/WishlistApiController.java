package com.praveen.praveenmart.controller.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.praveen.praveenmart.dto.ApiResponse;
import com.praveen.praveenmart.exception.ResourceNotFoundException;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.User;
import com.praveen.praveenmart.model.WishlistItem;
import com.praveen.praveenmart.service.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.praveen.praveenmart.util.JsonUtil;
import java.util.Map;

/**
 * REST API for Wishlist operations versioned under /api/v1/wishlist (Section 13).
 */
@WebServlet(name = "WishlistApiController", urlPatterns = {"/api/v1/wishlist", "/api/v1/wishlist/*"})
public class WishlistApiController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(WishlistApiController.class);
    private final Gson gson = JsonUtil.getGson();
    private WishlistService wishlistService = new WishlistService();

    @Override
    public void init() {
        this.wishlistService = new WishlistService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        User user = getSessionUser(request);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(ApiResponse.fail("UNAUTHORIZED", "Please sign in to view wishlist.")));
            return;
        }

        try {
            List<WishlistItem> items = wishlistService.getUserWishlist(user.getId());
            int count = wishlistService.getWishlistCount(user.getId());

            Map<String, Object> data = new HashMap<>();
            data.put("items", items);
            data.put("count", count);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(ApiResponse.ok(data)));

        } catch (Exception e) {
            logger.error("Error fetching wishlist for user {}", user.getId(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(ApiResponse.fail("SERVER_ERROR", "Could not load wishlist.")));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        User user = getSessionUser(request);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(ApiResponse.fail("UNAUTHORIZED", "Please sign in to modify wishlist.")));
            return;
        }

        try {
            JsonObject body = parseRequestBody(request);
            Long productId = body.has("productId") ? body.get("productId").getAsLong() : null;
            if (productId == null && request.getParameter("productId") != null) {
                productId = Long.parseLong(request.getParameter("productId"));
            }

            String action = body.has("action") ? body.get("action").getAsString() : request.getParameter("action");

            if (productId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(gson.toJson(ApiResponse.fail("VALIDATION_ERROR", "Product ID is required.")));
                return;
            }

            if ("move".equalsIgnoreCase(action) || "move-to-cart".equalsIgnoreCase(action)) {
                wishlistService.moveToCart(user.getId(), productId);
                int count = wishlistService.getWishlistCount(user.getId());

                Map<String, Object> data = new HashMap<>();
                data.put("message", "Product moved to shopping cart.");
                data.put("wishlistCount", count);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(ApiResponse.ok(data)));
                return;
            }

            wishlistService.addToWishlist(user.getId(), productId);
            int count = wishlistService.getWishlistCount(user.getId());

            Map<String, Object> data = new HashMap<>();
            data.put("message", "Product added to wishlist.");
            data.put("wishlistCount", count);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(ApiResponse.ok(data)));

        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(ApiResponse.fail("VALIDATION_ERROR", e.getMessage())));
        } catch (ResourceNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(gson.toJson(ApiResponse.fail("NOT_FOUND", e.getMessage())));
        } catch (Exception e) {
            logger.error("Error updating wishlist", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(ApiResponse.fail("SERVER_ERROR", "Could not update wishlist.")));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        User user = getSessionUser(request);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(gson.toJson(ApiResponse.fail("UNAUTHORIZED", "Please sign in.")));
            return;
        }

        String idParam = request.getParameter("productId");
        if (idParam == null) {
            String pathInfo = request.getPathInfo();
            if (pathInfo != null && pathInfo.length() > 1) {
                idParam = pathInfo.substring(1);
            }
        }

        if (idParam == null || idParam.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson(ApiResponse.fail("VALIDATION_ERROR", "Product ID is required.")));
            return;
        }

        try {
            Long productId = Long.parseLong(idParam.trim());
            wishlistService.removeFromWishlist(user.getId(), productId);
            int count = wishlistService.getWishlistCount(user.getId());

            Map<String, Object> data = new HashMap<>();
            data.put("message", "Product removed from wishlist.");
            data.put("wishlistCount", count);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(ApiResponse.ok(data)));

        } catch (Exception e) {
            logger.error("Error removing from wishlist", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(ApiResponse.fail("SERVER_ERROR", "Could not remove item from wishlist.")));
        }
    }

    private JsonObject parseRequestBody(HttpServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String body = sb.toString().trim();
            if (!body.isEmpty()) {
                JsonObject obj = com.praveen.praveenmart.util.JsonUtil.getGson().fromJson(body, JsonObject.class);
                return obj != null ? obj : new JsonObject();
            }
        } catch (Exception ignored) {
        }
        return new JsonObject();
    }

    private User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }
}
