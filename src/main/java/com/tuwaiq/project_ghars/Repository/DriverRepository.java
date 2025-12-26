package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    Driver findDriverById(Integer id);
}
