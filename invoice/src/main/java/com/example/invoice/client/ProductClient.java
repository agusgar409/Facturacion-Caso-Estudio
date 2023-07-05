package com.example.invoice.client;

import com.example.invoice.model.response.ProductResponse;
import errors.NotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import models.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "products-microservice")
public interface ProductClient {


    @PostMapping("/products/calcTotal")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "productCB", fallbackMethod = "errorProductCalcTotalFallback")
    Double calcTotal(@RequestBody List<ProductRequest> productRequestList);

    @GetMapping("/products/{code}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "productCB", fallbackMethod = "errorGetProductFallback")
    ProductResponse getProducts(@Valid @PathVariable("code") int code);

    default Double errorProductCalcTotalFallback(List<ProductRequest> productRequestList, Exception exc) {
        System.out.println("aca ocurrio un error con calc total");
        throw new NotFoundException(productRequestList);
    }

    default ProductResponse errorGetProductFallback(int code, Exception exc){
        System.out.println("aca ocurrio un error obtener producto");
        throw new NotFoundException(code);
    }

}
