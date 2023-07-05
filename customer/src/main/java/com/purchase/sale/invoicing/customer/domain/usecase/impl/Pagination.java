package com.purchase.sale.invoicing.customer.domain.usecase.impl;

import com.purchase.sale.invoicing.customer.domain.model.Customer;
import com.purchase.sale.invoicing.customer.domain.model.CustomerList;
import com.purchase.sale.invoicing.customer.domain.repository.CustomerRepository;
import com.purchase.sale.invoicing.customer.ports.input.rs.api.ApiConstants;
import com.purchase.sale.invoicing.customer.ports.input.rs.mapper.CustomerMapper;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequestFilter;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponse;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseList;
import com.purchase.sale.invoicing.customer.ports.input.rs.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class Pagination {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final CustomerSpecification spec;


    @Transactional(readOnly = true)
    public CustomerResponseList getAllCustomerByPageAndFilter(PageRequest pageRequest, CustomerRequestFilter filter) {
        Specification<Customer> customerSpec = spec.getAllSpecification(filter);
        Page<Customer> page = repository.findAll(customerSpec, pageRequest);
        CustomerList customerList = new CustomerList(page.getContent(), pageRequest, page.getTotalElements());
        return buildOrderList(customerList);
    }

    private CustomerResponseList buildOrderList(CustomerList customerList) {
        return CustomerResponseList.builder()
                .content(getContent(customerList))
                .totalPages(customerList.getTotalPages())
                .totalElements(customerList.getTotalElements())
                .nextUri(getNextPage(customerList))
                .previousUri(getPreviousPage(customerList))
                .build();
    }

    private List<CustomerResponse> getContent(CustomerList list) {
        return mapper.listCustomerToListCustomerResponse(list.getContent());
    }

    private String getNextPage(CustomerList customerlist) {
        final int nextPage = customerlist.getPageable().next().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }

    private String getPreviousPage(CustomerList customerlist) {
        final int previousPage = customerlist.getPageable().previousOrFirst().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(previousPage);
    }
}
