package com.surya.reviewMS.review;

import java.util.List;

public interface ReviewService {
     List<Review> getAllReviews(Long compId);
     boolean createReview(Long compId,Review review);

     Review getReviewById(Long reviewId);
     boolean updateReview( Long reviewId, Review review);

     boolean deleteReview( Long reviewId);
}
