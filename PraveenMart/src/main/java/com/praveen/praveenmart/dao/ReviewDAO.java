package com.praveen.praveenmart.dao;

import com.praveen.praveenmart.model.Review;

import java.util.List;

public interface ReviewDAO {

    List<Review> findByProductId(Long productId);

    boolean createReview(Review review);

    double getAverageRating(Long productId);

    int countReviews(Long productId);
}
