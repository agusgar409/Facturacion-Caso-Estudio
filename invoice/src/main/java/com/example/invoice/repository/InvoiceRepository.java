package com.example.invoice.repository;

import com.example.invoice.model.InvoiceEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> , JpaSpecificationExecutor<InvoiceEntity> {
    Optional<InvoiceEntity> getByNumber(String id);

    @Query(value = "SELECT max(idInvoice) FROM InvoiceEntity ")
    Long getLast();

    List<InvoiceEntity> findAll(Specification<InvoiceEntity> specification);


}
