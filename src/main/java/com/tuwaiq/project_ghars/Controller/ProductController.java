package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.AddProductDTOIn;
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

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProducts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(productService.getAllProducts(user.getId()));
    }

    @GetMapping("/my-products")
    public ResponseEntity<?> getMyProducts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(productService.getMyProducts(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal User user, @Valid @RequestBody AddProductDTOIn dto) {
        productService.addProduct(user.getId(), dto);
        return ResponseEntity.ok(new ApiResponse("Product added successfully")
        );
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId, @Valid @RequestBody Product product) {
        productService.updateProduct(user.getId(), productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal User user, @PathVariable Integer productId) {
        productService.deleteProduct(user.getId(), productId);
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
