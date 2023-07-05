package com.purchase.sale.invoicing.customer.domain.usecase.impl;


import com.purchase.sale.invoicing.customer.common.exception.errors.EmailAlreadyBoundException;
import com.purchase.sale.invoicing.customer.domain.model.Customer;
import com.purchase.sale.invoicing.customer.domain.model.CustomerList;
import com.purchase.sale.invoicing.customer.domain.model.CustomerType;
import com.purchase.sale.invoicing.customer.domain.model.Direction;
import com.purchase.sale.invoicing.customer.domain.repository.CustomerRepository;
import com.purchase.sale.invoicing.customer.domain.usecase.CustomerService;
import com.purchase.sale.invoicing.customer.domain.usecase.CustomerTypeService;
import com.purchase.sale.invoicing.customer.ports.input.rs.api.ApiConstants;
import com.purchase.sale.invoicing.customer.ports.input.rs.mapper.CustomerMapper;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.CustomerRequestFilter;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponse;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseList;
import com.purchase.sale.invoicing.customer.ports.input.rs.specification.CustomerSpecification;
import errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jrodriguez
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerTypeService typeService;


    @Transactional
    @Override
    public Long saveCustomer(Customer customer) {
        checkEmailAvailability(customer.getEmail());
        customer.setType(getCustomerTypeSet(customer.getType()));
        return repository.save(customer).getId();
    }

    private void checkEmailAvailability(String email) {
        if (repository.existsByEmail(email)) throw new EmailAlreadyBoundException(email);
    }

    /**
     * @param typeList
     * @return lista que contiene el/los tipo/s de cliente/s.
     */
    private Set<CustomerType> getCustomerTypeSet(Set<CustomerType> typeList) {
        return typeList.stream()
                .map(t -> typeService.getByIdIfExist(t.getId()))
                .collect(Collectors.toSet());
    }


    @Transactional
    @Override
    public void update(Long id, Customer customerRequest) {
        Customer exist = getById(id);

        updateCustomerProperties(exist, customerRequest);
        if (customerRequest.getDirection() != null)
            updateDirectionOfCustomer(exist.getDirection(), customerRequest.getDirection());
        Optional.ofNullable(customerRequest.getType()).ifPresent(exist::setType);

        repository.save(exist);
    }

    private void updateDirectionOfCustomer(Direction dirExist, Direction dirUpdate) {
        if (dirUpdate.getStreet() != null) dirExist.setStreet(dirUpdate.getStreet().toUpperCase());
        Optional.ofNullable(dirUpdate.getHeight()).ifPresent(dirExist::setHeight);
        Optional.ofNullable(dirUpdate.getProvince()).ifPresent(dirExist::setProvince);
        Optional.ofNullable(dirUpdate.getLocation()).ifPresent(dirExist::setLocation);
        Optional.of(dirUpdate.getPostalCode()).ifPresent(dirExist::setPostalCode);
    }


    private void updateCustomerProperties(Customer exist, Customer customerUpdate) {
        checkEmailAvailability(customerUpdate.getEmail());
        if (customerUpdate.getName() != null) exist.setName(customerUpdate.getName().toUpperCase());
        if (customerUpdate.getLastname() != null) exist.setLastname(customerUpdate.getLastname().toUpperCase());
        Optional.ofNullable(customerUpdate.getDni()).ifPresent(exist::setDni);
        Optional.ofNullable(customerUpdate.getEmail()).ifPresent(exist::setEmail);
    }

    @Transactional
    @Override
    public void deleted(Long id) {
        Customer exists = getById(id);
        repository.delete(exists);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }



}
