package com.purchase.sale.invoicing.customer.domain.usecase;

import com.purchase.sale.invoicing.customer.domain.model.CustomerType;

/**
 * @author jrodriguez
 */
public interface CustomerTypeService {

    CustomerType getByIdIfExist(Long id);
}
