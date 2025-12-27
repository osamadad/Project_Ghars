package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOout.InvoiceDTOout;
import com.tuwaiq.project_ghars.Model.Invoice;
import com.tuwaiq.project_ghars.Model.Order;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.InvoiceRepository;
import com.tuwaiq.project_ghars.Repository.OrderRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<InvoiceDTOout> getAllInvoices(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Access denied");

        return invoiceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTOout> getMyInvoices(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("CUSTOMER"))
            throw new ApiException("Only customer can access invoices");

        return invoiceRepository
                .findInvoiceByOrder_Customer_Id(user.getCustomer().getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void createInvoice(Integer userId, Integer orderId, Invoice invoice) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Only admin can create invoice");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        invoice.setOrder(order);
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Integer userId, Integer invoiceId, Invoice invoice) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Only admin can update invoice");

        Invoice oldInvoice = invoiceRepository.findInvoiceById(invoiceId);
        if (oldInvoice == null)
            throw new ApiException("Invoice not found");

        oldInvoice.setStatus(invoice.getStatus());
        oldInvoice.setCurrency(invoice.getCurrency());
        oldInvoice.setSubTotal(invoice.getSubTotal());
        oldInvoice.setTotal(invoice.getTotal());

        invoiceRepository.save(oldInvoice);
    }

    public void deleteInvoice(Integer userId, Integer invoiceId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Only admin can delete invoice");

        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        if (invoice == null)
            throw new ApiException("Invoice not found");

        invoiceRepository.delete(invoice);
    }

    private InvoiceDTOout convertToDTO(Invoice invoice) {
        return new InvoiceDTOout(
                invoice.getId(),
                invoice.getStatus(),
                invoice.getCurrency(),
                invoice.getSubTotal(),
                invoice.getTotal(),
                invoice.getOrder().getId(),
                invoice.getOrder().getStatus(),
                invoice.getOrder().getCreatedAt()
        );
    }
}
