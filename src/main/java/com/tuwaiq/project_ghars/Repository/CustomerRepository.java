package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
