package com.purchase.sale.invoicing.customer.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jrodriguez
 */
public class CustomerList extends PageImpl<Customer> {

    public CustomerList(List<Customer> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

}
