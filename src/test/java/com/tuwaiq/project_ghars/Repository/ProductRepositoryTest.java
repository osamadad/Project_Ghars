package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    FarmRepository farmRepository;
    @Autowired
    FarmerRepository farmerRepository;
    @Autowired
    UserRepository userRepository;

    User user;
    Farmer farmer;
    Farm farm;
    Product product1, product2, product3;
    List<Product> products;

    @BeforeEach
    void setUp() {

        user = new User(null, "osama", "osama@gmail.com", "osama", "osamadad768", "0500000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        userRepository.save(user);
        farmer = new Farmer(null, "beginner", 1000, 500, 200, null, null, user, null, null);
        farmerRepository.save(farmer);
        farm = new Farm(null, "123456789", "Pending", "happy farm", "happy farm", "Small", "apples", 5.5, "https", LocalDateTime.now(), farmer, null, null, null);
        farmRepository.save(farm);

        product1 = new Product(null, "Apple", "Fresh apples", 10.0, "HARVESTED_PRODUCT", true, "https://apple.photo", null, farm);

        product2 = new Product(null, "Tomato Seedling", "Young tomato plant", 5.0, "PLANT_SEEDLING", true, "https://tomato.photo", null, farm);

        product3 = new Product(null, "Basil Seeds", "Organic basil seeds", 2.0, "SEEDS", true, "https://basil.photo", null, farm);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @Test
    public void findProductById() {
        Product product = productRepository.findProductById(product1.getId());
        Assertions.assertThat(product.getId()).isEqualTo(product1.getId());
    }

    @Test
    public void findProductBySellType() {
        products = productRepository.findProductBySellType("SEEDS");
        Assertions.assertThat(products.get(0)).isEqualTo(product3);
    }

    @Test
    public void findAllByOrderByPriceAsc() {
        products = productRepository.findAllByOrderByPriceAsc();
        Assertions.assertThat(products).containsExactly(product3, product2, product1);
    }

    @Test
    public void getProductWithMinAndMaxPrice() {
        products = productRepository.getProductWithMinAndMaxPrice(3, 10);
        Assertions.assertThat(products).containsExactly(product2, product1);
    }
}
