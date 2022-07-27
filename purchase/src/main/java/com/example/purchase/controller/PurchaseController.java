package com.example.purchase.controller;
import com.example.purchase.model.response.PurchaseResponse;
import com.example.purchase.model.request.PurchaseOrderRequest;
import com.example.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.models.OrderRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller @RequestMapping("/purchase") @RestController @RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    @GetMapping("/{id}") @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PurchaseResponse> getPurchaseOrder(@Valid @PathVariable String id){return ResponseEntity.status(HttpStatus.FOUND).body(purchaseService.searchPurchase(id));}
    @GetMapping("/all") @ResponseStatus(HttpStatus.OK)
    public List<PurchaseResponse> getAllPurchaseOrders(){return purchaseService.searchAllPurchases();}
    @PatchMapping("/update/{number}") @ResponseStatus(HttpStatus.CONTINUE)
    public void updatePurchaseOrder(@Valid @NotNull @PathVariable String number, @Valid @RequestBody PurchaseOrderRequest request){purchaseService.updateOrder(number, request);}
    @PostMapping() @ResponseStatus(HttpStatus.CREATED)
    public void createPurchaseOrder(@RequestBody OrderRequest inputOrder){purchaseService.generateOrder(inputOrder);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.OK)
    public void deletePurchaseOrder(@Valid @PathVariable String id){purchaseService.deletePurchaseOrder(id);}
    @PutMapping("/status/{number}/{status}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatusPurchaseOrder(@Valid @PathVariable String number, @Valid @PathVariable Long status){purchaseService.changeStatusOrder(number,status);}
}