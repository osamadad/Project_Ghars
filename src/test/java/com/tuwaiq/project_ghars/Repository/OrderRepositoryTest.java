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
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;

    User user1,user2;
    Customer customer1, customer2;
    Order order1, order2, order3;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        user1 = new User(null, "osama", "osama@gmail.com", "osama", "osamadad768", "0500000000", "CUSTOMER", LocalDateTime.now(), null, null, null, null, null);
        user2 = new User(null, "mohammed", "mohammed@gmail.com", "mohammed", "mohammeddad768", "0550000000", "CUSTOMER", LocalDateTime.now(), null, null, null, null, null);
        userRepository.save(user1);
        userRepository.save(user2);
        customer1 = new Customer(null, "Apples", user1, null);
        customer2 = new Customer(null, "Orange", user2, null);
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        order1 = new Order(null, "PAID", 150.0, LocalDateTime.now(), null, customer1, null);
        order2 = new Order(null, "SHIPPED", 300.0, LocalDateTime.now(), null, customer1, null);
        order3 = new Order(null, "PENDING_PAYMENT", 100.0, LocalDateTime.now(), null, customer2, null);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
    }

    @Test
    public void findOrderById() {
        Order order = orderRepository.findOrderById(order1.getId());
        Assertions.assertThat(order.getId()).isEqualTo(order1.getId());
    }

    @Test
    public void findOrderByCustomer_Id() {
        orders = orderRepository.findOrderByCustomer_Id(customer1.getId());

        Assertions.assertThat(orders).hasSize(2);
        Assertions.assertThat(orders).contains(order1, order2);
    }
}
