package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    OrderItem findOrderItemById(Integer id);

    List<OrderItem> findOrderItemByOrder_Id(Integer orderId);
}
