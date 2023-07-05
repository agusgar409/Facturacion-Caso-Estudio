package com.micro.sale.domain.repository;

import com.micro.sale.domain.model.SaleOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jrodriguez
 */
public interface SaleOrderItemRepository  extends JpaRepository<SaleOrderItem, Long> {

    List<SaleOrderItem> findByIdOrder (Long idOrder);
}
