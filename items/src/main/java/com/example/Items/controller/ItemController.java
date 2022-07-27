package com.example.Items.controller;

import com.example.Items.model.filter.OrderRequestFilter;
import com.example.Items.model.response.OrderResponseList;
import com.example.Items.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping()
    public ResponseEntity<List<Long>> newItem(@RequestBody OrderRequest orderRequest) {

        List<Long> listItems;

        listItems = itemService.createItems(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(listItems);
    }

    @GetMapping()
    public ResponseEntity<OrderResponseList> getItem(@RequestParam(required = false, name = "product") Integer idProduct,
                                                     @RequestParam(required = false, name = "category") Long idCategory,
                                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                                     @RequestParam(defaultValue = "10", required = false) Integer size) {

        OrderRequestFilter filter = new OrderRequestFilter(idProduct, idCategory);
        OrderResponseList list = itemService.getAllOrdersByPageAndFilter(PageRequest.of(page, size), filter);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/edit/{isSupplier}")
    public ResponseEntity<Boolean> editStockProducts(@RequestBody @Valid ItemsEditDto itemsEditDto, @PathVariable boolean isSupplier) {

        Boolean response = itemService.editStockProducts(itemsEditDto, isSupplier);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
