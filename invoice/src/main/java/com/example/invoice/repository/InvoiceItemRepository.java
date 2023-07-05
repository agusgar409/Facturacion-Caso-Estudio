package com.example.invoice.repository;

import com.example.invoice.model.InvoiceItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItems,Long> {

    List<InvoiceItems> findByIdInvoice(Long idInvoice);
}
