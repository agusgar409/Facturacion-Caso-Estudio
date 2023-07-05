package com.example.purchase.domain.clients;

import errors.CreationClassException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import models.ItemsEditDto;
import models.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
@FeignClient(name = "items-microservice")
public interface ItemClient {

    @PutMapping("/item/edit/{isSupplier}")    @CircuitBreaker(name = "itemCB", fallbackMethod = "errorItemEditStockFallback")
    ResponseEntity<Boolean> editStockProducts(@RequestBody @Valid ItemsEditDto itemsEditDto, @PathVariable boolean isSupplier);

    @PostMapping("/item")    @CircuitBreaker(name = "itemCB", fallbackMethod = "errorItemFallback")
    ResponseEntity<List<Long>> newItem(@RequestBody OrderRequest orderRequest);

    default ResponseEntity<Boolean> errorItemFallback(OrderRequest orderRequest, Exception exc){
        throw new CreationClassException(orderRequest);
    }

    default ResponseEntity<List<Long>> errorItemEditStockFallback(ItemsEditDto itemsEditDto, boolean isSupplier, Exception exc){
        throw new CreationClassException(itemsEditDto);
    }
}
