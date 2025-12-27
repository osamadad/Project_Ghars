package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    Stock findStockById(Integer id);

    List<Stock> findStockByProduct_Farm_Farmer_Id(Integer farmerId);
}
