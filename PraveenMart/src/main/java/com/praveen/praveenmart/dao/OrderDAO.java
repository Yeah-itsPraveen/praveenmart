package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.model.Order;
import com.praveen.praveenmart.model.OrderItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    Long createOrder(Connection conn, Order order) throws SQLException;

    boolean createOrderItem(Connection conn, OrderItem item) throws SQLException;

    boolean deductProductStock(Connection conn, Long productId, int quantity) throws SQLException;

    Order findById(Long orderId);

    List<Order> findByBuyerId(Long buyerId);

    List<Order> findAll();

    List<OrderItem> findItemsByOrderId(Long orderId);

    List<OrderItem> findItemsBySellerId(Long sellerId);

    boolean updateOrderStatus(Long orderId, String status);

    int countOrders();

    int countOrdersBySeller(Long sellerId);

    BigDecimal calculateTotalRevenue();

    BigDecimal calculateSellerRevenue(Long sellerId);
}
