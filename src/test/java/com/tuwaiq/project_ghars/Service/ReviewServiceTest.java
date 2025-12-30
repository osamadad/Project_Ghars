package com.tuwaiq.project_ghars.Service;


import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.Review;
import com.tuwaiq.project_ghars.Repository.FarmRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmerRepository farmerRepository;

    Farmer farmer;
    Farm farm;
    Review review1, review2;
    List<Review> reviews;

    @BeforeEach
    public void setUp() {

        farmer = new Farmer(1, "beginner", 100, 200, 300, null, null, null, null, null);

        farm = new Farm(null, "123456789", "Pending", "happy farm", "happy farm", "Small", "apples", 5.5, "https", LocalDateTime.now(), farmer, null, null, null);

        review1 = new Review(1, "Great farm", "Very clean", 5, LocalDateTime.now(), farm);
        review2 = new Review(2, "Good", "Nice experience", 4, LocalDateTime.now(), farm);

        reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
    }

    @Test
    public void addReview() {
        when(farmerRepository.findFarmerById(farmer.getId())).thenReturn(farmer);
        when(farmRepository.findFarmById(farm.getId())).thenReturn(farm);

        reviewService.addReview(farmer.getId(), farm.getId(), review1);

        verify(farmerRepository, times(1)).findFarmerById(farmer.getId());
        verify(farmRepository, times(1)).findFarmById(farm.getId());
        verify(reviewRepository, times(1)).save(review1);
    }

    @Test
    public void getAllReviews() {
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviews();

        Assertions.assertEquals(reviews, result);
        verify(reviewRepository, times(1)).findAll();
    }
}
