package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Invoice;
import com.tuwaiq.project_ghars.Service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<?> getAllInvoices(@PathVariable Integer userId) {
        return ResponseEntity.status(200)
                .body(invoiceService.getAllInvoices(userId));
    }

    @GetMapping("/my-invoices/{userId}")
    public ResponseEntity<?> getMyInvoices(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(invoiceService.getMyInvoices(userId));
    }

    @PostMapping("/create/{userId}/{orderId}")
    public ResponseEntity<?> createInvoice(@PathVariable Integer userId, @PathVariable Integer orderId, @Valid @RequestBody Invoice invoice) {
        invoiceService.createInvoice(userId, orderId, invoice);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice created successfully"));
    }

    @PutMapping("/update/{userId}/{invoiceId}")
    public ResponseEntity<?> updateInvoice(@PathVariable Integer userId, @PathVariable Integer invoiceId, @Valid @RequestBody Invoice invoice) {
        invoiceService.updateInvoice(userId, invoiceId, invoice);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice updated successfully"));
    }

    @DeleteMapping("/delete/{userId}/{invoiceId}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Integer userId, @PathVariable Integer invoiceId) {
        invoiceService.deleteInvoice(userId, invoiceId);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice deleted successfully"));
    }
}
