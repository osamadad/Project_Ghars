package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Delivery findDeliveryById(Integer id);

    Delivery findDeliveryByOrder_Id(Integer orderId);

    List<Delivery> findDeliveryByDriver_Id(Integer driverId);
}
