package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Review;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.FarmRepository;
import com.tuwaiq.project_ghars.Repository.ReviewRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;


    public void addReview(Integer userId, Integer farmId, Review review) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("CUSTOMER"))
//            throw new ApiException("Only customer can add review");

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null)
            throw new ApiException("Farm not found");

        review.setFarm(farm);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    public List<Review> getReviewsByFarm(Integer farmId) {

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null)
            throw new ApiException("Farm not found");

        return reviewRepository.findReviewByFarm_Id(farmId);
    }
}
