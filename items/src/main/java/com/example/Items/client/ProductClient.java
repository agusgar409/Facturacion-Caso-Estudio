package com.example.Items.client;

import errors.CreationClassException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import models.ItemEditStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

/**
 * @author agustin
 */
@FeignClient(name = "PRODUCTS-MICROSERVICE")
public interface ProductClient {

    @PutMapping(value = "/products/editStock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CircuitBreaker(name = "productCB", fallbackMethod = "editStockFallback")
    void editStockProducts(@Valid @RequestBody ItemEditStock orderRequest);

    default void editStockFallback(ItemEditStock purchaseRequest, Exception exc) {
        System.out.println("error editing stock products" + exc.getMessage());
        throw new CreationClassException(purchaseRequest);
    }
}
