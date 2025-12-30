package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.UpdateStockQuantityDTOIn;
import com.tuwaiq.project_ghars.Model.Stock;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.StockRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    public List<Stock> getAllStocks(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Access denied");

        return stockRepository.findAll();
    }

    public List<Stock> getMyStock(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("FARMER"))
//            throw new ApiException("Only farmer can access this");

        return stockRepository.findStockByProduct_Farm_Farmer_Id(
                user.getFarmer().getId()
        );
    }

    public void addStockQuantity(Integer userId, Integer stockId, UpdateStockQuantityDTOIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (user.getFarmer() == null)
            throw new ApiException("Only farmers can update stock");

        Stock stock = stockRepository.findStockById(stockId);
        if (stock == null)
            throw new ApiException("Stock not found");


        if (!stock.getProduct().getFarm().getFarmer().getId()
                .equals(user.getFarmer().getId()))
            throw new ApiException("This stock does not belong to you");

        stock.setTotalQuantity(
                stock.getTotalQuantity() + dto.getAddedQuantity()
        );

        stock.setLastUpdate(LocalDateTime.now());
        stockRepository.save(stock);
    }


    public void updateStock(Integer userId, Integer stockId, Stock stock) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!(user.getRole().equals("FARMER") || user.getRole().equals("ADMIN")))
//            throw new ApiException("Only farmer or admin can update stock");

        Stock oldStock = stockRepository.findStockById(stockId);
        if (oldStock == null)
            throw new ApiException("Stock not found");

        if (stock.getTotalQuantity() < 0)
            throw new ApiException("Stock quantity cannot be negative");

        oldStock.setTotalQuantity(stock.getTotalQuantity());
        oldStock.setUnit(stock.getUnit());
        oldStock.setLastUpdate(LocalDateTime.now());

        stockRepository.save(oldStock);
    }

    public void deleteStock(Integer userId, Integer stockId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Only admin can delete stock");

        Stock stock = stockRepository.findStockById(stockId);
        if (stock == null)
            throw new ApiException("Stock not found");

        stockRepository.delete(stock);
    }
}
