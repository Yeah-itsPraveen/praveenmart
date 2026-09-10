package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.dao.impl.WishlistDAOImpl;
import com.praveen.praveenmart.model.WishlistItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WishlistDAOTest extends BaseDAOTest {

    private WishlistDAO wishlistDAO;
    private final Long testUserId = 3L; // buyer@praveenmart.com in seed.sql
    private final Long testProductId = 1L;

    @BeforeEach
    void setUp() {
        wishlistDAO = new WishlistDAOImpl();
        wishlistDAO.clearWishlist(testUserId);
    }

    @Test
    @DisplayName("Should add product to wishlist and check existence")
    void testAddToWishlistAndCheck() {
        boolean added = wishlistDAO.addToWishlist(testUserId, testProductId);
        assertTrue(added, "Product should be added to wishlist");

        boolean exists = wishlistDAO.isInWishlist(testUserId, testProductId);
        assertTrue(exists, "Product should exist in wishlist");

        int count = wishlistDAO.getWishlistCount(testUserId);
        assertEquals(1, count, "Wishlist count should be 1");
    }

    @Test
    @DisplayName("Should retrieve all saved wishlist items for user")
    void testFindByUserId() {
        wishlistDAO.addToWishlist(testUserId, 1L);
        wishlistDAO.addToWishlist(testUserId, 2L);

        List<WishlistItem> items = wishlistDAO.findByUserId(testUserId);
        assertNotNull(items);
        assertEquals(2, items.size(), "User should have 2 wishlist items");
        assertNotNull(items.get(0).getProduct(), "Joined product should not be null");
    }

    @Test
    @DisplayName("Should remove product from wishlist")
    void testRemoveFromWishlist() {
        wishlistDAO.addToWishlist(testUserId, testProductId);
        assertTrue(wishlistDAO.isInWishlist(testUserId, testProductId));

        boolean removed = wishlistDAO.removeFromWishlist(testUserId, testProductId);
        assertTrue(removed, "Product should be removed");
        assertFalse(wishlistDAO.isInWishlist(testUserId, testProductId), "Product should no longer be in wishlist");
    }
}
