package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Review;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add/{farmId}")
    public ResponseEntity<?> addReview(@AuthenticationPrincipal User user, @PathVariable Integer farmId, @RequestBody @Valid Review review) {
        reviewService.addReview(user.getId(), farmId, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @GetMapping("/get-by-farm/{farmId}")
    public ResponseEntity<?> getMyReviewsByFarm(@AuthenticationPrincipal User user, @PathVariable Integer farmId) {

        return ResponseEntity.status(200).body(reviewService.getMyReviewsByFarm(user.getId(), farmId));
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal User user, @PathVariable Integer reviewId, @RequestBody @Valid Review review) {

        reviewService.updateReview(user.getId(), reviewId, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal User user, @PathVariable Integer reviewId) {

        reviewService.deleteReview(user.getId(), reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }
}
