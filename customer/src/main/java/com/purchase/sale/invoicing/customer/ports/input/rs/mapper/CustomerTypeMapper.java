package com.purchase.sale.invoicing.customer.ports.input.rs.mapper;

import com.purchase.sale.invoicing.customer.domain.model.CustomerType;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerTypeRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerTypeResponse;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author jrodriguez
 */
@Mapper
public interface CustomerTypeMapper {

    Set<CustomerTypeResponse> toCustomerTypeResponse(Set<CustomerType> typeSet);

    CustomerTypeResponse toCustomerTypeResponse(CustomerType type);

    Set<CustomerType> toCustomerModelList(Set<CustomerTypeRequest> typeSet);

    CustomerType toCustomerModel(CustomerTypeRequest type);
}
