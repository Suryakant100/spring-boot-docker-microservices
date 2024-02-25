package com.surya.reviewMS.review.impl;


import com.surya.reviewMS.review.Review;
import com.surya.reviewMS.review.ReviewRepository;
import com.surya.reviewMS.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews(Long compId) {
        return reviewRepository.findByCompanyId(compId);
    }

    @Override
    public boolean createReview(Long compId,Review review) {
        if(compId!=null && review!=null){
            review.setCompanyId(compId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview( Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteReview( Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null ){
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }
}
