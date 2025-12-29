package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.PaymentRequestDTOIn;
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

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Value("${moyasar.secret-key}")
    private String moyasarSecretKey;

    private static final String MOYASAR_API_UTL="https://api.moyasar.com/v1/payments";


    public ResponseEntity<String> startPayment(Integer userId, Integer orderId, PaymentRequestDTOIn paymentRequest) {

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

        String url="https://api.moyasar.com/v1/payments";

        String callBackUrl="https://your-server.com/api/payments/callback";

        Payment payment = new Payment();
        payment.setAmount(order.getTotalPrice());
        payment.setCurrency("SAR");
        payment.setDescription("Payment for order "+order.getId()+" amount to "+ payment.getAmount());


        String requestBody=String.format(
                "source[type]=card" +
                        "&source[name]=%s" +
                        "&source[number]=%s" +
                        "&source[cvc]=%s" +
                        "&source[month]=%s" +
                        "&source[year]=%s" +
                        "&amount=%d" +
                        "&currency=%s" +
                        "&description=%s" +
                        "&callback_url=%s",
                paymentRequest.getName(),
                paymentRequest.getNumber(),
                paymentRequest.getCvc(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                (int) (payment.getAmount() * 100),
                payment.getCurrency(),
                payment.getDescription(),
                callBackUrl
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(moyasarSecretKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );


        payment.setStatus("PENDING");
        payment.setProvider("MOYASAR");
        payment.setCallBackUrl(callBackUrl);
        payment.setOrder(order);

        paymentRepository.save(payment);

        // Return the API response
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    public String getPaymentStatus(String paymentId) {

        String url="https://api.moyasar.com/v1/payments";

        // Prepare headers with authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(moyasarSecretKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Call Moyasar API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url +"/"+ paymentId,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Return the response body
        return response.getBody();
    }
}