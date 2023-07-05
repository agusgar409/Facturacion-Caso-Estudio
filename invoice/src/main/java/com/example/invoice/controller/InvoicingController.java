package com.example.invoice.controller;

import com.example.invoice.api.ApiConstants;
import com.example.invoice.dto.InvoiceDto;
import com.example.invoice.model.filter.OrderRequestFilter;
import com.example.invoice.model.response.OrderResponseList;
import com.example.invoice.service.InvoiceService;
import errors.CategoryInvalidException;
import errors.StatusInvalidException;
import models.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstants.ORDER_URI)
public class InvoicingController {

    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    @PostMapping()
    public ResponseEntity<InvoiceDto> newInvoice(@Valid @RequestBody OrderRequest orderRequest) throws CategoryInvalidException, StatusInvalidException {

        InvoiceDto numberInvoice = invoiceService.createOrderRequest(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(numberInvoice);

    }

    @GetMapping()
    public ResponseEntity<OrderResponseList> getOrder(@RequestParam(required = false) String number,
                                                      @RequestParam(required = false) Long customer,
                                                      @RequestParam(required = false) Long status,
                                                      @RequestParam(required = false, name = "category") Integer idCategory,
                                                      @RequestParam(defaultValue = "0", required = false) Integer page,
                                                      @RequestParam(defaultValue = "10", required = false) Integer size){

        OrderRequestFilter filter = new OrderRequestFilter(number, customer, status, idCategory);
        OrderResponseList list = invoiceService.getAllOrdersByPageAndFilter(PageRequest.of(page, size), filter);
        return ResponseEntity.ok(list);

    }

    @PutMapping("/{number}")
    public ResponseEntity<InvoiceDto> updateInvoiceStatus(@PathVariable String number,@Valid @RequestParam Long status){

        InvoiceDto invoiceResponse = invoiceService.updateInvoice(number,status);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(invoiceResponse);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<Boolean> deleteInvoice(@Valid @PathVariable String number){

        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.deleteInvoice(number));

    }

}
