package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Stock;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStocks(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(stockService.getAllStocks(user.getId()));
    }

    @GetMapping("/my-stock")
    public ResponseEntity<?> getMyStock(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(stockService.getMyStock(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@AuthenticationPrincipal User user, @RequestBody @Valid Stock stock) {
        stockService.addStock(user.getId(), stock);
        return ResponseEntity.status(200).body(new ApiResponse("Stock added successfully"));
    }

    @PutMapping("/update/{stockId}")
    public ResponseEntity<?> updateStock(@AuthenticationPrincipal User user, @PathVariable Integer stockId, @RequestBody @Valid Stock stock) {
        stockService.updateStock(user.getId(), stockId, stock);
        return ResponseEntity.status(200).body(new ApiResponse("Stock updated successfully"));
    }

    @DeleteMapping("/delete/{stockId}")
    public ResponseEntity<?> deleteStock(@AuthenticationPrincipal User user, @PathVariable Integer stockId) {
        stockService.deleteStock(user.getId(), stockId);
        return ResponseEntity.status(200).body(new ApiResponse("Stock deleted successfully"));
    }
}
