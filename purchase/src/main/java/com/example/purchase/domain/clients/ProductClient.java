package com.example.purchase.domain.clients;

import errors.CreationClassException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import models.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "products-microservice")
public interface ProductClient {

     @RequestMapping(value = "/products/calcTotal", method = RequestMethod.POST)@CircuitBreaker(name = "productCB", fallbackMethod = "errorCalcTotal")
     ResponseEntity<Double> calcTotal(@Valid @RequestBody List<ProductRequest> productRequestList);

     default ResponseEntity<List<Long>> errorCalcTotal(List<ProductRequest> productRequestList, Exception exc){
          throw new CreationClassException(productRequestList);
     }
}
