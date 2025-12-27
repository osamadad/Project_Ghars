package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Stock;
import com.tuwaiq.project_ghars.Service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<?> getAllStocks(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(stockService.getAllStocks(userId));
    }


    @GetMapping("/my-stock/{userId}")
    public ResponseEntity<?> getMyStock(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(stockService.getMyStock(userId));
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addStock(@PathVariable Integer userId, @RequestBody @Valid Stock stock) {
        stockService.addStock(userId, stock);
        return ResponseEntity.status(200).body(new ApiResponse("Stock added successfully"));
    }

    @PutMapping("/update/{userId}/{stockId}")
    public ResponseEntity<?> updateStock(@PathVariable Integer userId, @PathVariable Integer stockId, @RequestBody @Valid  Stock stock) {
        stockService.updateStock(userId, stockId, stock);
        return ResponseEntity.status(200).body(new ApiResponse("Stock updated successfully"));
    }

    @DeleteMapping("/delete/{userId}/{stockId}")
    public ResponseEntity<?> deleteStock(@PathVariable Integer userId, @PathVariable Integer stockId) {
        stockService.deleteStock(userId, stockId);
        return ResponseEntity.status(200).body(new ApiResponse("Stock deleted successfully"));
    }
}
