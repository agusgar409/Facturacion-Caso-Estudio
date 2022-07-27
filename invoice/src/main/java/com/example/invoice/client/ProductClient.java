package com.example.invoice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import request.ProductRequest;
import util.models.ItemEditStock;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "products-microservice")
public interface ProductClient {

    @RequestMapping(value = "/products/editStock", method = RequestMethod.PUT)
    boolean editStockProducts(@Valid @RequestBody ItemEditStock purchaseRequest);

    @RequestMapping(value = "/products/calcTotal", method = RequestMethod.POST)
    Double calcTotal(@Valid @RequestBody List<ProductRequest> productRequestList);

}
