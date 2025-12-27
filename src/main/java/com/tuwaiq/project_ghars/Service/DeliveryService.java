package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;


    public List<Delivery> getMyDeliveries(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("DRIVER"))
            throw new ApiException("Access denied");

        return deliveryRepository.findDeliveryByDriver_Id(
                user.getDriver().getId()
        );
    }

    public void createDelivery(Integer userId, Integer orderId, Integer driverId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("FARMER"))
            throw new ApiException("Only farmer can create delivery");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (order.getDelivery() != null)
            throw new ApiException("Delivery already exists for this order");

        if (!(order.getStatus().equals("PAID") || order.getStatus().equals("CONFIRMED")))
            throw new ApiException("Order is not ready for delivery");

        Driver driver = driverRepository.findDriverById(driverId);
        if (driver == null)
            throw new ApiException("Driver not found");

        if (driver.getIsBusy())
            throw new ApiException("Driver is busy");

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDriver(driver);
        delivery.setStatus("PENDING");

        order.setDelivery(delivery);

        driver.setIsBusy(true);

        deliveryRepository.save(delivery);
    }


    public void updateDeliveryStatus(Integer userId, Integer deliveryId, String status) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("DRIVER"))
            throw new ApiException("Only driver can update delivery status");

        Delivery delivery = deliveryRepository.findDeliveryById(deliveryId);
        if (delivery == null)
            throw new ApiException("Delivery not found");

        if (!delivery.getDriver().getId().equals(user.getDriver().getId()))
            throw new ApiException("This delivery does not belong to you");

        delivery.setStatus(status);

        if (status.equals("DELIVERED")) {
            delivery.getDriver().setIsBusy(false);
            delivery.getOrder().setStatus("DELIVERED");
        }

        deliveryRepository.save(delivery);
    }

}
