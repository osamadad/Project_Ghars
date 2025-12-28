package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Product;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<?> getAllProducts(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(productService.getAllProducts(userId));
    }

    @GetMapping("/my-products/{userId}")
    public ResponseEntity<?> getMyProducts(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(productService.getMyProducts(userId));
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addProduct(@PathVariable Integer userId, @Valid @RequestBody Product product) {
        productService.addProduct(userId, product);
        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
    }

    @PutMapping("/update/{userId}/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer userId, @PathVariable Integer productId, @Valid @RequestBody Product product) {
        productService.updateProduct(userId, productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
    }

    @DeleteMapping("/delete/{userId}/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer userId, @PathVariable Integer productId) {
        productService.deleteProduct(userId, productId);
        return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
    }

    @GetMapping("/by-sell-type/{sellType}")
    public ResponseEntity<?> getProductsBySellType(@AuthenticationPrincipal User user, @PathVariable String sellType) {
        return ResponseEntity.status(200).body(productService.getProductsBySellType(user.getId(), sellType));
    }

    @GetMapping("/order-by-price")
    public ResponseEntity<?> getProductsOrderByPrice(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(productService.getProductsOrderByPriceAsc(user.getId()));
    }

    @GetMapping("/price-range/{minPrice}/{maxPrice}")
    public ResponseEntity<?> getProductsByPriceRange(@AuthenticationPrincipal User user, @PathVariable Integer minPrice, @PathVariable Integer maxPrice) {
        return ResponseEntity.status(200).body(productService.getProductsByMinAndMaxPrice(user.getId(), minPrice, maxPrice));
    }
}
