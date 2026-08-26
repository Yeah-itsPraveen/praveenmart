package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.model.CartItem;

import java.util.List;

public interface CartDAO {

    List<CartItem> findByUserId(Long userId);

    CartItem findByUserAndProduct(Long userId, Long productId);

    boolean addToCart(Long userId, Long productId, int quantity);

    boolean updateQuantity(Long cartItemId, int quantity);

    boolean removeFromCart(Long cartItemId, Long userId);

    boolean clearCart(Long userId);

    int getCartItemCount(Long userId);
}
