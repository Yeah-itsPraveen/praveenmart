package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.CartDAO;
import com.praveen.praveenmart.dao.ProductDAO;
import com.praveen.praveenmart.dao.WishlistDAO;
import com.praveen.praveenmart.exception.ResourceNotFoundException;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

    @Mock
    private WishlistDAO wishlistDAO;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private CartDAO cartDAO;

    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        wishlistService = new WishlistService(wishlistDAO, productDAO, cartDAO);
    }

    @Test
    @DisplayName("Should throw ValidationException when userId or productId is null on add")
    void testAddToWishlistValidation() {
        assertThrows(ValidationException.class, () -> wishlistService.addToWishlist(null, 1L));
        assertThrows(ValidationException.class, () -> wishlistService.addToWishlist(1L, null));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product does not exist")
    void testAddToWishlistNonExistentProduct() {
        when(productDAO.findById(999L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> wishlistService.addToWishlist(1L, 999L));
    }

    @Test
    @DisplayName("Should successfully move product from wishlist to cart")
    void testMoveToCartSuccess() {
        Product p = new Product();
        p.setId(10L);
        p.setName("Test Shirt");
        p.setStockQty(5);
        p.setPrice(new BigDecimal("499.00"));

        when(productDAO.findById(10L)).thenReturn(p);
        when(cartDAO.addToCart(1L, 10L, 1)).thenReturn(true);
        when(wishlistDAO.removeFromWishlist(1L, 10L)).thenReturn(true);

        boolean moved = wishlistService.moveToCart(1L, 10L);
        assertTrue(moved);

        verify(cartDAO).addToCart(1L, 10L, 1);
        verify(wishlistDAO).removeFromWishlist(1L, 10L);
    }

    @Test
    @DisplayName("Should fail moving product if stock is zero")
    void testMoveToCartOutOfStock() {
        Product p = new Product();
        p.setId(10L);
        p.setName("Out of stock shirt");
        p.setStockQty(0);

        when(productDAO.findById(10L)).thenReturn(p);

        assertThrows(ValidationException.class, () -> wishlistService.moveToCart(1L, 10L));
        verify(cartDAO, never()).addToCart(anyLong(), anyLong(), anyInt());
    }
}
