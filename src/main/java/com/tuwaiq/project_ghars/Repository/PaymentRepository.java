package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findPaymentById(Integer id);

    Payment findPaymentByOrder_Id(Integer orderId);

    Payment findPaymentByMoyasarPaymentId(String moyasarPaymentId);
}
