package com.praveen.praveenmart.dao.impl;

import com.praveen.praveenmart.dao.WishlistDAO;
import com.praveen.praveenmart.model.Product;
import com.praveen.praveenmart.model.WishlistItem;
import com.praveen.praveenmart.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAOImpl implements WishlistDAO {

    private static final Logger logger = LoggerFactory.getLogger(WishlistDAOImpl.class);

    @Override
    public boolean addToWishlist(Long userId, Long productId) {
        String checkSql = "SELECT id FROM wishlist_items WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO wishlist_items (user_id, product_id) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setLong(1, userId);
                checkStmt.setLong(2, productId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        return true; // Already in wishlist
                    }
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setLong(1, userId);
                insertStmt.setLong(2, productId);
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            logger.error("Error adding product to wishlist: userId={}, productId={}", userId, productId, e);
        }
        return false;
    }

    @Override
    public boolean removeFromWishlist(Long userId, Long productId) {
        String sql = "DELETE FROM wishlist_items WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error removing product from wishlist: userId={}, productId={}", userId, productId, e);
        }
        return false;
    }

    @Override
    public List<WishlistItem> findByUserId(Long userId) {
        List<WishlistItem> items = new ArrayList<>();
        String sql = """
                SELECT w.id, w.user_id, w.product_id, w.created_at,
                       p.name, p.description, p.price, p.stock_qty, p.category, p.image_url, p.seller_id
                FROM wishlist_items w
                JOIN products p ON w.product_id = p.id
                WHERE w.user_id = ?
                ORDER BY w.id DESC
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    WishlistItem item = new WishlistItem();
                    item.setId(rs.getLong("id"));
                    item.setUserId(rs.getLong("user_id"));
                    item.setProductId(rs.getLong("product_id"));
                    if (rs.getTimestamp("created_at") != null) {
                        item.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }

                    Product product = new Product();
                    product.setId(rs.getLong("product_id"));
                    product.setSellerId(rs.getLong("seller_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getBigDecimal("price"));
                    product.setStockQty(rs.getInt("stock_qty"));
                    product.setCategory(rs.getString("category"));
                    product.setImageUrl(rs.getString("image_url"));

                    item.setProduct(product);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding wishlist items for userId={}", userId, e);
        }
        return items;
    }

    @Override
    public boolean isInWishlist(Long userId, Long productId) {
        String sql = "SELECT COUNT(*) FROM wishlist_items WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error checking wishlist status: userId={}, productId={}", userId, productId, e);
        }
        return false;
    }

    @Override
    public int getWishlistCount(Long userId) {
        String sql = "SELECT COUNT(*) FROM wishlist_items WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Error counting wishlist items for userId={}", userId, e);
        }
        return 0;
    }

    @Override
    public void clearWishlist(Long userId) {
        String sql = "DELETE FROM wishlist_items WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error clearing wishlist for userId={}", userId, e);
        }
    }
}
