package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOout.PaymentDTOOut;
import com.tuwaiq.project_ghars.Model.Order;
import com.tuwaiq.project_ghars.Model.Payment;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.OrderRepository;
import com.tuwaiq.project_ghars.Repository.PaymentRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Value("${moyasar.secret-key}")
    private String moyasarSecretKey;

    public PaymentDTOOut startPayment(Integer userId, Integer orderId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("CUSTOMER"))
            throw new ApiException("Only customer can make payment");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (!order.getCustomer().getId().equals(user.getCustomer().getId()))
            throw new ApiException("This order does not belong to you");

        if (!order.getStatus().equals("PENDING_PAYMENT"))
            throw new ApiException("Order is not pending payment");

        Payment existing = paymentRepository.findPaymentByOrder_Id(orderId);
        if (existing != null && existing.getStatus().equals("PAID"))
            throw new ApiException("Order already paid");

        Payment payment = new Payment();
        payment.setStatus("PENDING");
        payment.setProvider("MOYASAR");
        payment.setAmount(order.getTotalPrice());
        payment.setOrder(order);
        paymentRepository.save(payment);

        int amountInHalala = order.getTotalPrice() * 100;

        Map<String, Object> body = new HashMap<>();
        body.put("amount", amountInHalala);
        body.put("currency", "SAR");
        body.put("description", "Order #" + order.getId());

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("local_payment_id", payment.getId());
        metadata.put("order_id", order.getId());
        body.put("metadata", metadata);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(moyasarSecretKey, "");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.moyasar.com/v1/payments",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map res = response.getBody();
        if (res == null || res.get("id") == null)
            throw new ApiException("Moyasar payment failed");

        String moyasarPaymentId = res.get("id").toString();
        payment.setMoyasarPaymentId(moyasarPaymentId);
        paymentRepository.save(payment);

        return new PaymentDTOOut(
                payment.getId(),
                payment.getStatus(),
                payment.getProvider(),
                payment.getAmount(),
                payment.getMoyasarPaymentId()
        );
    }
}