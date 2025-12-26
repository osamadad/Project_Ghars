package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    Address findAddressesById(Integer id);
}
