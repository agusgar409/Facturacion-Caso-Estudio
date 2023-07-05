package com.purchase.sale.invoicing.customer.domain.repository;

import com.purchase.sale.invoicing.customer.domain.model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
