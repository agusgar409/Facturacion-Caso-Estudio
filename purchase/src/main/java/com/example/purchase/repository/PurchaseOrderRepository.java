package com.example.purchase.repository;


import com.example.purchase.model.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>,  JpaSpecificationExecutor<PurchaseOrder> {
    List<PurchaseOrder> findAll(Specification<PurchaseOrder> spec);
    @Query(value = "SELECT max(id) FROM PurchaseOrder")
    Long getLast();
    Optional<PurchaseOrder> getByNumber(String number);
}
