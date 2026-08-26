package com.praveen.praveenmart.service;

import com.praveen.praveenmart.dao.ReviewDAO;
import com.praveen.praveenmart.dao.impl.ReviewDAOImpl;
import com.praveen.praveenmart.exception.ValidationException;
import com.praveen.praveenmart.model.Review;

import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService() {
        this(new ReviewDAOImpl());
    }

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<Review> getReviewsForProduct(Long productId) {
        if (productId == null) return List.of();
        return reviewDAO.findByProductId(productId);
    }

    public boolean addReview(Long productId, Long userId, int rating, String comment) {
        if (productId == null || userId == null) {
            throw new ValidationException("Product and User are required to submit a review.");
        }
        if (rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5 stars.");
        }

        Review review = new Review();
        review.setProductId(productId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setComment(comment != null ? comment.trim() : "");

        return reviewDAO.createReview(review);
    }

    public double getAverageRating(Long productId) {
        if (productId == null) return 0.0;
        return reviewDAO.getAverageRating(productId);
    }

    public int getReviewCount(Long productId) {
        if (productId == null) return 0;
        return reviewDAO.countReviews(productId);
    }
}
