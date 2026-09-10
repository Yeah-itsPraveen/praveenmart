package com.praveen.praveenmart.controller;

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
import java.io.IOException;
import java.util.List;

/**
 * Controller managing Wishlist views and user actions (Optional Feature O1).
 */
@WebServlet(name = "WishlistServlet", urlPatterns = {
        "/wishlist",
        "/wishlist/add",
        "/wishlist/remove",
        "/wishlist/move-to-cart"
})
public class WishlistServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServlet.class);
    private WishlistService wishlistService;

    @Override
    public void init() {
        this.wishlistService = new WishlistService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = getSessionUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?redirect=/wishlist");
            return;
        }

        String path = request.getServletPath();

        if ("/wishlist/remove".equals(path)) {
            handleRemove(request, response, user);
            return;
        }

        if ("/wishlist/move-to-cart".equals(path)) {
            handleMoveToCart(request, response, user);
            return;
        }

        List<WishlistItem> wishlistItems = wishlistService.getUserWishlist(user.getId());
        request.setAttribute("wishlistItems", wishlistItems);
        request.setAttribute("wishlistCount", wishlistItems.size());

        request.getRequestDispatcher("/wishlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = getSessionUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String path = request.getServletPath();

        switch (path) {
            case "/wishlist/add" -> handleAdd(request, response, user);
            case "/wishlist/remove" -> handleRemove(request, response, user);
            case "/wishlist/move-to-cart" -> handleMoveToCart(request, response, user);
            default -> response.sendRedirect(request.getContextPath() + "/wishlist");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            wishlistService.addToWishlist(user.getId(), productId);
            request.getSession().setAttribute("msgSuccess", "Item saved to your wishlist!");
        } catch (Exception e) {
            logger.error("Error adding product to wishlist", e);
            request.getSession().setAttribute("msgError", "Could not add item to wishlist.");
        }

        String redirect = request.getParameter("redirect");
        if (redirect != null && !redirect.isBlank()) {
            response.sendRedirect(request.getContextPath() + redirect);
        } else {
            response.sendRedirect(request.getContextPath() + "/wishlist");
        }
    }

    private void handleRemove(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            wishlistService.removeFromWishlist(user.getId(), productId);
            request.getSession().setAttribute("msgSuccess", "Item removed from wishlist.");
        } catch (Exception e) {
            logger.error("Error removing product from wishlist", e);
            request.getSession().setAttribute("msgError", "Could not remove item from wishlist.");
        }

        response.sendRedirect(request.getContextPath() + "/wishlist");
    }

    private void handleMoveToCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            wishlistService.moveToCart(user.getId(), productId);
            request.getSession().setAttribute("msgSuccess", "Item moved to your cart!");
        } catch (Exception e) {
            logger.error("Error moving product to cart", e);
            request.getSession().setAttribute("msgError", e.getMessage() != null ? e.getMessage() : "Could not move item to cart.");
        }

        response.sendRedirect(request.getContextPath() + "/wishlist");
    }

    private User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }
}
