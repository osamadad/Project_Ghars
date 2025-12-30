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
    public ResponseEntity<?> addReview(@AuthenticationPrincipal User user, @PathVariable Integer farmId, @RequestBody Review review) {

        reviewService.addReview(user.getId(), farmId, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<?> getReviewsByFarm(@PathVariable Integer farmId) {
        return ResponseEntity.status(200).body(reviewService.getReviewsByFarm(farmId));
    }
}
