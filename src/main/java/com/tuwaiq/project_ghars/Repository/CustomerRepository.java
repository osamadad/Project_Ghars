package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerById(Integer id);

}
