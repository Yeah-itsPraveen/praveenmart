package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.CartDAO;
import com.praveen.praveenmart.dao.OrderDAO;
import com.praveen.praveenmart.dao.ProductDAO;
import com.praveen.praveenmart.dao.impl.CartDAOImpl;
import com.praveen.praveenmart.dao.impl.OrderDAOImpl;
import com.praveen.praveenmart.dao.impl.ProductDAOImpl;
import com.praveen.praveenmart.exception.AppException;
import com.praveen.praveenmart.exception.InsufficientStockException;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.CartItem;
import com.praveen.praveenmart.model.Order;
import com.praveen.praveenmart.model.OrderItem;
import com.praveen.praveenmart.model.Product;
import com.praveen.praveenmart.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderDAO orderDAO;
    private final CartDAO cartDAO;
    private final ProductDAO productDAO;

    public OrderService() {
        this(new OrderDAOImpl(), new CartDAOImpl(), new ProductDAOImpl());
    }

    public OrderService(OrderDAO orderDAO, CartDAO cartDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
    }

    public Order placeOrder(Long buyerId, String shippingAddress, String paymentMethod) {
        if (buyerId == null) {
            throw new ValidationException("Buyer ID is required.");
        }

        List<CartItem> cartItems = cartDAO.findByUserId(buyerId);
        if (cartItems.isEmpty()) {
            throw new ValidationException("Cannot place order with an empty cart.");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            Product p = item.getProduct();
            if (p == null) {
                p = productDAO.findById(item.getProductId());
            }
            if (p == null) {
                throw new AppException("Product not found for ID: " + item.getProductId());
            }
            if (p.getStockQty() < item.getQuantity()) {
                throw new InsufficientStockException("Product '" + p.getName() + "' does not have enough stock.");
            }
            totalAmount = totalAmount.add(p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Order order = new Order();
                order.setBuyerId(buyerId);
                order.setStatus("CONFIRMED");
                order.setTotalAmount(totalAmount);

                Long orderId = orderDAO.createOrder(conn, order);
                order.setId(orderId);

                for (CartItem item : cartItems) {
                    Product p = item.getProduct() != null ? item.getProduct() : productDAO.findById(item.getProductId());

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setProductId(p.getId());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setUnitPrice(p.getPrice());

                    orderDAO.createOrderItem(conn, orderItem);

                    boolean stockUpdated = orderDAO.deductProductStock(conn, p.getId(), item.getQuantity());
                    if (!stockUpdated) {
                        throw new InsufficientStockException("Failed to update stock for product: " + p.getName());
                    }
                }

                cartDAO.clearCart(buyerId);

                conn.commit();
                logger.info("Order placed successfully: orderId={}, buyerId={}, total={}", orderId, buyerId, totalAmount);
                return getOrderById(orderId);

            } catch (Exception e) {
                conn.rollback();
                logger.error("Transaction rolled back for order creation buyerId={}", buyerId, e);
                if (e instanceof AppException) {
                    throw (AppException) e;
                }
                throw new AppException("Failed to complete order checkout: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error("Database error during order placement", e);
            throw new AppException("Database error during order placement: " + e.getMessage(), e);
        }
    }

    public Order getOrderById(Long orderId) {
        return orderDAO.findById(orderId);
    }

    public List<Order> getBuyerOrders(Long buyerId) {
        if (buyerId == null) return List.of();
        return orderDAO.findByBuyerId(buyerId);
    }

    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    public List<OrderItem> getSellerIncomingOrders(Long sellerId) {
        if (sellerId == null) return List.of();
        return orderDAO.findItemsBySellerId(sellerId);
    }

    public boolean updateOrderStatus(Long orderId, String newStatus) {
        if (orderId == null || newStatus == null || newStatus.isBlank()) {
            return false;
        }
        return orderDAO.updateOrderStatus(orderId, newStatus.trim().toUpperCase());
    }

    public int getTotalOrdersCount() {
        return orderDAO.countOrders();
    }

    public int getSellerOrdersCount(Long sellerId) {
        if (sellerId == null) return 0;
        return orderDAO.countOrdersBySeller(sellerId);
    }

    public BigDecimal getTotalPlatformRevenue() {
        return orderDAO.calculateTotalRevenue();
    }

    public BigDecimal getSellerRevenue(Long sellerId) {
        if (sellerId == null) return BigDecimal.ZERO;
        return orderDAO.calculateSellerRevenue(sellerId);
    }
}
