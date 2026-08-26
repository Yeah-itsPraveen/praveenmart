package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.model.Product;

import java.util.List;

public interface ProductDAO {

    List<Product> findAll();

    List<Product> findByCategory(String category);

    List<Product> search(String keyword, String category);

    List<Product> findBySellerId(Long sellerId);

    Product findById(Long id);

    boolean createProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(Long id, Long sellerId);

    boolean adminDeleteProduct(Long id);

    int countProducts();

    int countProductsBySeller(Long sellerId);
}
