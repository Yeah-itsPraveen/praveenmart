package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.CartDAO;
import com.praveen.praveenmart.dao.DAOFactory;
import com.praveen.praveenmart.dao.ProductDAO;
import com.praveen.praveenmart.dao.WishlistDAO;
import com.praveen.praveenmart.exception.ResourceNotFoundException;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.Product;
import com.praveen.praveenmart.model.WishlistItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service managing user wishlist / save-for-later operations (Optional Feature O1).
 */
public class WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);
    private final WishlistDAO wishlistDAO;
    private final ProductDAO productDAO;
    private final CartDAO cartDAO;

    public WishlistService() {
        this(DAOFactory.getWishlistDAO(), DAOFactory.getProductDAO(), DAOFactory.getCartDAO());
    }

    public WishlistService(WishlistDAO wishlistDAO, ProductDAO productDAO, CartDAO cartDAO) {
        this.wishlistDAO = wishlistDAO;
        this.productDAO = productDAO;
        this.cartDAO = cartDAO;
    }

    /**
     * Adds a product to the user's wishlist.
     *
     * @param userId    the ID of the user
     * @param productId the ID of the product to save
     * @return true if added successfully, false otherwise
     */
    public boolean addToWishlist(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new ValidationException("User ID and Product ID are required for wishlist.");
        }
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }
        boolean success = wishlistDAO.addToWishlist(userId, productId);
        if (success) {
            logger.info("Product {} added to wishlist for user {}", productId, userId);
        }
        return success;
    }

    /**
     * Removes a product from the user's wishlist.
     *
     * @param userId    the ID of the user
     * @param productId the ID of the product to remove
     * @return true if removed successfully
     */
    public boolean removeFromWishlist(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new ValidationException("User ID and Product ID are required.");
        }
        return wishlistDAO.removeFromWishlist(userId, productId);
    }

    /**
     * Retrieves all wishlist items for a given user.
     *
     * @param userId the ID of the user
     * @return list of WishlistItem objects
     */
    public List<WishlistItem> getUserWishlist(Long userId) {
        if (userId == null) return List.of();
        return wishlistDAO.findByUserId(userId);
    }

    /**
     * Checks whether a product is currently in the user's wishlist.
     *
     * @param userId    the ID of the user
     * @param productId the ID of the product
     * @return true if product is wishlisted
     */
    public boolean isInWishlist(Long userId, Long productId) {
        if (userId == null || productId == null) return false;
        return wishlistDAO.isInWishlist(userId, productId);
    }

    /**
     * Counts the total items saved in the user's wishlist.
     *
     * @param userId the ID of the user
     * @return count of items
     */
    public int getWishlistCount(Long userId) {
        if (userId == null) return 0;
        return wishlistDAO.getWishlistCount(userId);
    }

    /**
     * Moves a product from wishlist to cart by adding 1 unit to cart and removing from wishlist.
     *
     * @param userId    the ID of the user
     * @param productId the ID of the product to move
     * @return true if moved successfully
     */
    public boolean moveToCart(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new ValidationException("User ID and Product ID are required.");
        }
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }
        if (product.getStockQty() <= 0) {
            throw new ValidationException("Cannot move out-of-stock item '" + product.getName() + "' to cart.");
        }

        cartDAO.addToCart(userId, productId, 1);
        wishlistDAO.removeFromWishlist(userId, productId);
        logger.info("Product {} moved from wishlist to cart for user {}", productId, userId);
        return true;
    }
}
