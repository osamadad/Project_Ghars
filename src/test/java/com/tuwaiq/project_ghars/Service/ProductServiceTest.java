package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.Product;
import com.tuwaiq.project_ghars.Model.Stock;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.ProductRepository;
import com.tuwaiq.project_ghars.Repository.StockRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private UserRepository userRepository;

    User user1, user2;
    Farmer farmer1;
    Product product1, product2;
    Stock stock1, stock2;
    List<Product> products;
    List<Stock> stocks;

    @BeforeEach
    public void setUp() {

        user1 = new User(1, "admin", "admin@gmail.com", "admin", "admin123", "0500000000", "ADMIN", LocalDateTime.now(), null, null, null, null, null);

        farmer1 = new Farmer(2, "beginner", 1000, 500, 200, null, null, user2, null, null);
        user2 = new User(2, "farmer", "farmer@gmail.com", "farmer", "farmer123", "0550000000", "FARMER", LocalDateTime.now(), farmer1, null, null, null, null);

        stock1 = new Stock(1, 50, "PIECE",LocalDateTime.now(),null,null);

        stock2 = new Stock(1, 30, "PIECE",LocalDateTime.now(),null,null);

        product1 = new Product(1, "Apple", "Fresh apple", 10.0, "HARVESTED_PRODUCT", true, "photo", stock1, null);

        product2 = new Product(2, "Tomato", "Fresh tomato", 5.0, "SEEDS", true, "photo", stock2, null);

        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
    }

    @Test
    public void getAllProducts() {
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts(user1.getId());

        Assertions.assertEquals(products, result);
        verify(userRepository, times(1)).findUserById(user1.getId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getMyProducts() {
        when(userRepository.findUserById(user2.getId())).thenReturn(user2);
        when(stockRepository.findStockByProduct_Farm_Farmer_Id(user2.getId())).thenReturn(stocks);

        List<Product> result = productService.getMyProducts(user2.getId());

        Assertions.assertEquals(2, result.size());
        verify(userRepository, times(1)).findUserById(user2.getId());
        verify(stockRepository, times(1)).findStockByProduct_Farm_Farmer_Id(2);
    }

    @Test
    public void addProduct() {
        when(userRepository.findUserById(user2.getId())).thenReturn(user2);

        productService.addProduct(user2.getId(), product1);

        verify(userRepository, times(1)).findUserById(user2.getId());
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    public void updateProduct() {
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(productRepository.findProductById(product1.getId())).thenReturn(product1);

        Product updatedProduct = new Product(null, "Updated", "Updated desc", 20.0, "SEEDS", false, "newPhoto", null, null);

        productService.updateProduct(user1.getId(), product1.getId(), updatedProduct);

        Assertions.assertEquals("Updated", product1.getName());
        Assertions.assertEquals(20.0, product1.getPrice());
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    public void deleteProduct() {
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(productRepository.findProductById(product1.getId())).thenReturn(product1);

        productService.deleteProduct(user1.getId(), product1.getId());

        verify(productRepository, times(1)).delete(product1);
    }

    @Test
    public void getProductsBySellType() {
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(productRepository.findProductBySellType("SEEDS")).thenReturn(products);

        List<Product> result = productService.getProductsBySellType(user1.getId(), "SEEDS");

        Assertions.assertEquals(products, result);
        verify(productRepository, times(1)).findProductBySellType("SEEDS");
    }

    @Test
    public void getProductsOrderByPriceAsc() {
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(productRepository.findAllByOrderByPriceAsc()).thenReturn(products);

        List<Product> result = productService.getProductsOrderByPriceAsc(user1.getId());

        Assertions.assertEquals(products, result);
        verify(productRepository, times(1)).findAllByOrderByPriceAsc();
    }

    @Test
    public void getProductsByMinAndMaxPrice() {
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(productRepository.getProductWithMinAndMaxPrice(5, 15)).thenReturn(products);

        List<Product> result = productService.getProductsByMinAndMaxPrice(user1.getId(), 5, 15);

        Assertions.assertEquals(products, result);
        verify(productRepository, times(1)).getProductWithMinAndMaxPrice(5, 15);
    }
}
