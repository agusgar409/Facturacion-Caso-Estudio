package com.example.invoice.service;

import com.example.invoice.dto.InvoiceDto;
import com.example.invoice.model.InvoiceEntity;
import com.example.invoice.model.filter.OrderRequestFilter;
import com.example.invoice.model.response.OrderResponseList;
import errors.CategoryInvalidException;
import errors.StatusInvalidException;
import models.OrderRequest;
import org.springframework.data.domain.PageRequest;

import java.util.InvalidPropertiesFormatException;

public interface InvoiceService {
    InvoiceDto createOrderRequest(OrderRequest inputOrder) throws StatusInvalidException, CategoryInvalidException;

    Boolean deleteInvoice(String number);

    OrderResponseList getAllOrdersByPageAndFilter(PageRequest of, OrderRequestFilter filter);

    InvoiceDto updateInvoice(String number, Long status);

    InvoiceEntity getInvoiceNumber(String number);
}
