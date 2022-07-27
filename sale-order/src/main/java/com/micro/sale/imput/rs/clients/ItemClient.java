package com.micro.sale.imput.rs.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import javax.validation.Valid;
import java.util.List;
/**
 * @author jrodriguez
 */
@FeignClient(name = "items-microservice")
public interface ItemClient {

    @PutMapping("/item/edit/{isSupplier}")
    ResponseEntity<Boolean> editStockProducts(@RequestBody @Valid ItemsEditDto itemsEditDto, @PathVariable boolean isSupplier);

    @PostMapping("/item")
    ResponseEntity<List<Long>> newItem(@RequestBody OrderRequest orderRequest);

}
