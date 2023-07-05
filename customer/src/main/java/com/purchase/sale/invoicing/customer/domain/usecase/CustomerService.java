package com.purchase.sale.invoicing.customer.domain.usecase;

import com.purchase.sale.invoicing.customer.domain.model.Customer;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequestFilter;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseList;
import org.springframework.data.domain.PageRequest;

/**
 * @author jrodriguez
 */
public interface CustomerService {
    Long saveCustomer(Customer newCustomer);

    void deleted(Long id);

    Customer getById(Long id);

    void update(Long id, Customer request);


}
