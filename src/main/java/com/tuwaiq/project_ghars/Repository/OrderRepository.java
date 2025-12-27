package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderById(Integer id);

    List<Order> findOrderByCustomer_Id(Integer customerId);
}
