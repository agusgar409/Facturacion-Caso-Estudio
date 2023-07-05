package com.example.purchase.infrastructure.controller;


import com.example.purchase.application.service.PurchaseService;
import com.example.purchase.domain.model.request.OrderRequestFilter;
import com.example.purchase.domain.model.response.OrderResponseList;
import com.example.purchase.domain.model.response.PurchaseResponse;
import com.example.purchase.domain.model.request.PurchaseOrderRequest;
import com.example.purchase.application.service.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import models.OrderRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller @RequestMapping("/purchase") @RestController @RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    private final Pagination pagination;

    @GetMapping("/{id}") @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PurchaseResponse> getPurchaseOrder(@Valid @PathVariable String id){return ResponseEntity.status(HttpStatus.FOUND).body(purchaseService.searchPurchase(id));}
    @GetMapping("/all") @ResponseStatus(HttpStatus.OK)
    public OrderResponseList getAllPurchaseOrders(@RequestParam(required = false) String number,
                                                  @RequestParam(required = false) Long customer,
                                                  @RequestParam(required = false) Long status,
                                                  @RequestParam(required = false, name = "category") Integer idCategory,
                                                  @RequestParam(defaultValue = "0", required = false) Integer page,
                                                  @RequestParam(defaultValue = "10", required = false) Integer size){
        OrderRequestFilter filter = new OrderRequestFilter(number, customer, status, idCategory);
        return pagination.getAllOrdersByPageAndFilter(PageRequest.of(page, size), filter);

    }
    @PatchMapping("/update/{number}") @ResponseStatus(HttpStatus.CONTINUE)
    public void updatePurchaseOrder(@Valid @NotNull @PathVariable String number, @Valid @RequestBody PurchaseOrderRequest request){purchaseService.updateOrder(number, request);}
    @PostMapping() @ResponseStatus(HttpStatus.CREATED)
    public void createPurchaseOrder(@RequestBody OrderRequest inputOrder){purchaseService.generateOrder(inputOrder);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.OK)
    public void deletePurchaseOrder(@Valid @PathVariable String id){purchaseService.deletePurchaseOrder(id);}
    @PutMapping("/status/{number}/{status}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatusPurchaseOrder(@Valid @PathVariable String number, @Valid @PathVariable Long status){purchaseService.changeStatusOrder(number,status);}
}