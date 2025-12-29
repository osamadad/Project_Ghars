package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findInvoiceById(Integer id);

    List<Invoice> findInvoiceByOrder_Customer_Id(Integer customerId);
}
