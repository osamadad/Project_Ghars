package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findReviewByFarmIdAndFarm_Farmer_Id(Integer farmId, Integer id);

    Review findReviewById(Integer reviewId);

    List<Review> findReviewByFarm_Id(Integer farmId);

}
