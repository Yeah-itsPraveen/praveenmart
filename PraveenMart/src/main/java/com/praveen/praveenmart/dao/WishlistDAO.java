package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.model.WishlistItem;

import java.util.List;

public interface WishlistDAO {

    boolean addToWishlist(Long userId, Long productId);

    boolean removeFromWishlist(Long userId, Long productId);

    List<WishlistItem> findByUserId(Long userId);

    boolean isInWishlist(Long userId, Long productId);

    int getWishlistCount(Long userId);

    void clearWishlist(Long userId);
}
