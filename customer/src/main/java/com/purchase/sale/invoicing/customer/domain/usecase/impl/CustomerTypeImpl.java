package com.purchase.sale.invoicing.customer.domain.usecase.impl;

import com.purchase.sale.invoicing.customer.domain.model.CustomerType;
import com.purchase.sale.invoicing.customer.domain.repository.CustomerTypeRepository;
import com.purchase.sale.invoicing.customer.domain.usecase.CustomerTypeService;
import errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jrodriguez
 */
@RequiredArgsConstructor
@Service
public class CustomerTypeImpl implements CustomerTypeService {

    private final CustomerTypeRepository repo;


    @Transactional(readOnly = true)
    @Override
    public CustomerType getByIdIfExist(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
