package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductById(Integer id);

    List<Product> findProductBySellType(String sellType);

    List<Product> findAllByOrderByPriceAsc();

    @Query("select product from Product product where product.price>=?1 and product.price<=?2 order by product.price asc ")
    List<Product> getProductWithMinAndMaxPrice(Integer minPrice, Integer maxPrice);
}
