package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.AddProductDTOIn;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
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
    private final FarmerRepository farmerRepository;
private final FarmRepository farmRepository;

    public List<Product> getAllProducts(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN")) throw new ApiException("Access denied");

        return productRepository.findAll();
    }

    public List<Product> getMyProducts(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        if (!user.getRole().equals("FARMER")) throw new ApiException("Only farmer can access this");

        return stockRepository.findStockByProduct_Farm_Farmer_Id(user.getFarmer().getId()).stream().map(Stock::getProduct).collect(Collectors.toList());
    }

    public void addProduct(Integer userId, AddProductDTOIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");


        if (user.getFarmer() == null)
            throw new ApiException("Only farmers can add products");

        Farm farm = farmRepository.findFarmById(dto.getFarmId());
        if (farm == null)
            throw new ApiException("Farm not found");


        if (!farm.getFarmer().getId().equals(user.getFarmer().getId()))
            throw new ApiException("This farm does not belong to you");


        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSellType(dto.getSellType());
        product.setIsActive(dto.getIsActive());
        product.setPhotoUrl(dto.getPhotoUrl());
        product.setFarm(farm);


        Stock stock = new Stock();
        stock.setTotalQuantity(dto.getTotalQuantity());
        stock.setUnit(dto.getUnit());
        stock.setLastUpdate(LocalDateTime.now());
        stock.setProduct(product);

        product.setStock(stock);

        productRepository.save(product);
    }



    public void updateProduct(Integer userId, Integer productId, Product product) {

        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

//        if (!(user.getRole().equals("FARMER") || user.getRole().equals("ADMIN")))
//            throw new ApiException("Only farmer or admin can update product");

        Product oldProduct = productRepository.findProductById(productId);
        if (oldProduct == null) throw new ApiException("Product not found");

        if (product.getPrice() <= 0) throw new ApiException("Price must be greater than zero");

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
        if (user == null) throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN")) throw new ApiException("Only admin can delete product");

        Product product = productRepository.findProductById(productId);
        if (product == null) throw new ApiException("Product not found");

        productRepository.delete(product);
    }

    public List<Product> getProductsBySellType(Integer userId, String sellType) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return productRepository.findProductBySellType(sellType);
    }

    public List<Product> getProductsOrderByPriceAsc(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> getProductsByMinAndMaxPrice(Integer userId, Integer minPrice, Integer maxPrice) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (minPrice > maxPrice) {
            throw new ApiException("Sorry, minimum price cannot be greater than maximum price, please try again");
        }

        return productRepository.getProductWithMinAndMaxPrice(minPrice, maxPrice);
    }
}
