package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Invoice;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInvoices(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(invoiceService.getAllInvoices(user.getId()));
    }

    @GetMapping("/my-invoices")
    public ResponseEntity<?> getMyInvoices(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(invoiceService.getMyInvoices(user.getId()));
    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<?> createInvoice(@AuthenticationPrincipal User user, @PathVariable Integer orderId, @Valid @RequestBody Invoice invoice) {
        invoiceService.createInvoice(user.getId(), orderId, invoice);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice created successfully"));
    }

    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<?> updateInvoice(@AuthenticationPrincipal User user, @PathVariable Integer invoiceId, @Valid @RequestBody Invoice invoice) {
        invoiceService.updateInvoice(user.getId(), invoiceId, invoice);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice updated successfully"));
    }

    @DeleteMapping("/delete/{invoiceId}")
    public ResponseEntity<?> deleteInvoice(@AuthenticationPrincipal User user, @PathVariable Integer invoiceId) {
        invoiceService.deleteInvoice(user.getId(), invoiceId);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice deleted successfully"));
    }
}
