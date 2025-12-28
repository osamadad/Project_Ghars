package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.Review;
import com.tuwaiq.project_ghars.Repository.FarmRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;

    public void addReview(Integer userId, Integer farmId, Review review) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }

        if (!farm.getFarmer().getId().equals(farmer.getId())) {
            throw new ApiException("You don't own this farm");
        }

        review.setCreatedAt(LocalDateTime.now());
        review.setFarm(farm);

        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getMyReviewsByFarm(Integer userId, Integer farmId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }

        if (!farm.getFarmer().getId().equals(farmer.getId())) {
            throw new ApiException("You don't own this farm");
        }

        return reviewRepository.findReviewByFarmIdAndFarm_Farmer_Id(farmId, farmer.getId());
    }

    public void updateReview(Integer userId, Integer reviewId, Review newReview) {
        Review review = getReviewByFarmer(userId, reviewId);

        review.setTitle(newReview.getTitle());
        review.setDescription(newReview.getDescription());
        review.setRating(newReview.getRating());

        reviewRepository.save(review);
    }

    public void deleteReview(Integer userId, Integer reviewId) {
        Review review = getReviewByFarmer(userId, reviewId);
        reviewRepository.delete(review);
    }

    private Review getReviewByFarmer(Integer userId, Integer reviewId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new ApiException("Review not found");
        }

        Farm farm = farmRepository.findFarmById(review.getFarm().getId());
        if (farm == null) {
            throw new ApiException("Farm not found");
        }

        if (!farm.getFarmer().getId().equals(farmer.getId())) {
            throw new ApiException("You dont own this farm");
        }

        return review;
    }
}
