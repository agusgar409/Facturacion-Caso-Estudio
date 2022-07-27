package com.example.purchase.repository;

import com.example.purchase.model.PurchaseOrderItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseOrderItemRepository  extends JpaRepository<PurchaseOrderItem, Long> {
    List<PurchaseOrderItem> findAll(Specification<PurchaseOrderItem> spec);
    List<PurchaseOrderItem> findByIdOrder(Long idRequest);
}
