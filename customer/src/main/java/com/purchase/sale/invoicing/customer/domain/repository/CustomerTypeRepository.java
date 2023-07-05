package com.purchase.sale.invoicing.customer.domain.repository;

import com.purchase.sale.invoicing.customer.domain.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author jrodriguez
 */
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
