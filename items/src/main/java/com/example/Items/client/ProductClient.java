package com.example.Items.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.models.ItemEditStock;

import javax.validation.Valid;

@FeignClient(name = "products-microservice")
public interface ProductClient {

    @RequestMapping(value = "/products/editStock", method = RequestMethod.PUT)
    boolean editStockProducts(@Valid @RequestBody ItemEditStock purchaseRequest);

}
