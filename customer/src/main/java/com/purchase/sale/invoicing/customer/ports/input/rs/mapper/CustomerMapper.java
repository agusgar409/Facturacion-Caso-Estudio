package com.purchase.sale.invoicing.customer.ports.input.rs.mapper;

import com.purchase.sale.invoicing.customer.domain.model.Customer;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerUpdateRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponse;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author jrodriguez
 */
@Mapper(componentModel = "spring", uses = {CustomerTypeMapper.class, DirectionMapper.class})
public interface CustomerMapper {


    @Mappings({
            @Mapping(source = "directionRequest", target = "direction"),
            @Mapping(source = "typesRequest", target = "type")
    })
    Customer toCustomer(CustomerRequest request);

    @Mappings({
            @Mapping(source = "directionRequest", target = "direction"),
            @Mapping(source = "typesRequest", target = "type")
    })
    Customer updateToCustomer(CustomerUpdateRequest request);


    @Mappings({
            @Mapping(source = "direction", target = "directionResponse"),
            @Mapping(source = "type", target = "typeResponses")
    })
    CustomerResponseDetails customertoCustomerResponseDetail(Customer model);

    List<CustomerResponse> listCustomerToListCustomerResponse(List<Customer> listModel);

}
