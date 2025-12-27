package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Product;
import com.tuwaiq.project_ghars.Model.Stock;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.ProductRepository;
import com.tuwaiq.project_ghars.Repository.StockRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;


    public List<Product> getAllProducts(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Access denied");

        return productRepository.findAll();
    }

    public List<Product> getMyProducts(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("FARMER"))
            throw new ApiException("Only farmer can access this");

        return stockRepository
                .findStockByProduct_Farm_Farmer_Id(user.getFarmer().getId())
                .stream()
                .map(Stock::getProduct)
                .collect(Collectors.toList());
    }

    public void addProduct(Integer userId, Product product) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!(user.getRole().equals("FARMER") || user.getRole().equals("ADMIN")))
            throw new ApiException("Only farmer or admin can add product");

        if (product.getPrice() <= 0)
            throw new ApiException("Price must be greater than zero");

        if (product.getIsActive() == null)
            throw new ApiException("Product active status is required");

        if (product.getStock() == null)
            throw new ApiException("Product must have stock");

        product.getStock().setLastUpdate(LocalDateTime.now());
        productRepository.save(product);
    }

    public void updateProduct(Integer userId, Integer productId, Product product) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!(user.getRole().equals("FARMER") || user.getRole().equals("ADMIN")))
            throw new ApiException("Only farmer or admin can update product");

        Product oldProduct = productRepository.findProductById(productId);
        if (oldProduct == null)
            throw new ApiException("Product not found");

        if (product.getPrice() <= 0)
            throw new ApiException("Price must be greater than zero");

        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setSellType(product.getSellType());
        oldProduct.setIsActive(product.getIsActive());
        oldProduct.setPhotoUrl(product.getPhotoUrl());

        productRepository.save(oldProduct);
    }

    public void deleteProduct(Integer userId, Integer productId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Only admin can delete product");

        Product product = productRepository.findProductById(productId);
        if (product == null)
            throw new ApiException("Product not found");

        productRepository.delete(product);
    }
}
