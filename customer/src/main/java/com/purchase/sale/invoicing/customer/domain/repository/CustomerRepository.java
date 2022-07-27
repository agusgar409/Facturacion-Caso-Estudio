package com.purchase.sale.invoicing.customer.domain.repository;

import com.purchase.sale.invoicing.customer.domain.model.Customer;
import com.purchase.sale.invoicing.customer.domain.model.CustomerType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jrodriguez
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsByEmail(String email);

    List<Customer> findByType(CustomerType type);
    List<Customer> findAll(Specification<Customer> spec);

}
