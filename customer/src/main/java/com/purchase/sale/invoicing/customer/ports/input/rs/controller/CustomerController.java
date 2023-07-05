package com.purchase.sale.invoicing.customer.ports.input.rs.controller;

import com.purchase.sale.invoicing.customer.domain.usecase.CustomerService;
import com.purchase.sale.invoicing.customer.domain.usecase.impl.Pagination;
import com.purchase.sale.invoicing.customer.ports.input.rs.api.ApiConstants;
import com.purchase.sale.invoicing.customer.ports.input.rs.mapper.CustomerMapper;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequestFilter;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerUpdateRequest;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseDetails;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * @author jrodriguez
 */
@RestController
@RequestMapping(ApiConstants.Customer_URI)
@RequiredArgsConstructor
public class CustomerController {
    private final Pagination pagination;
    private final CustomerMapper mapper;
    private final CustomerService service;


    @PostMapping("/save")
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerRequest request) {

        final long id = service.saveCustomer(mapper.toCustomer(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletedCustomer(@Valid @NotNull @PathVariable Long id) {
        service.deleted(id);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@Valid @NotNull @PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        service.update(id, mapper.updateToCustomer(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDetails> getCustomerDetails(@Valid @NotNull @PathVariable Long id) {
        CustomerResponseDetails response = mapper.customertoCustomerResponseDetail(service.getById(id));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/type_customer")
    public ResponseEntity<CustomerResponseList> getByTypeCustomers(@Valid @NotNull @RequestParam Long idType,
                                                                   @RequestParam(defaultValue = "0", required = false) Integer page,
                                                                   @RequestParam(defaultValue = "10", required = false) Integer size) {

        CustomerRequestFilter filter = CustomerRequestFilter.builder()
                .idType(idType)
                .build();
        CustomerResponseList listResponse = pagination.getAllCustomerByPageAndFilter(PageRequest.of(page, size), filter);
        return ResponseEntity.ok().body(listResponse);
    }

    @Transactional(readOnly = true)
    @GetMapping("/all")
    public ResponseEntity<CustomerResponseList> getAllCustomer(CustomerRequestFilter filter,
                                                               @RequestParam(defaultValue = "0", required = false) Integer page,
                                                               @RequestParam(defaultValue = "10", required = false) Integer size) {
        CustomerResponseList listResponse = pagination.getAllCustomerByPageAndFilter(PageRequest.of(page, size), filter);
        return ResponseEntity.ok().body(listResponse);
    }


}
