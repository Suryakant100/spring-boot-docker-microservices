package com.surya.reviewMS.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        List<Review> allReview = reviewService.getAllReviews(companyId);
        if(allReview.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allReview,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReviews(@RequestParam Long companyId, @RequestBody Review review){
        boolean isSaved = reviewService.createReview(companyId,review);
        if(isSaved){
            return new ResponseEntity<>("Review has benn created successfully.",HttpStatus.CREATED);
        }
        return  new ResponseEntity<>("Something went wrong, you might have entered wrong input",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public  ResponseEntity<Review> getReviewById( @PathVariable Long reviewId){
        Review review = reviewService.getReviewById(reviewId);
        if(review!=null){
            return new ResponseEntity<>(review,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,@RequestBody Review review){
        boolean isUpdated = reviewService.updateReview(reviewId,review);
        if(isUpdated){
            return new ResponseEntity<>("Review Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Nor Updates, You might have entered wrong input.",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview( @PathVariable Long reviewId){
        boolean isDeleted = reviewService.deleteReview(reviewId);
        if(isDeleted){
            return new ResponseEntity<>("Review Deleted Successfully.",HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went Wrong.",HttpStatus.NOT_FOUND);
    }
}
