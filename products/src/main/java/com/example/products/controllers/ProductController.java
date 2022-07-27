package com.example.products.controllers;

import com.example.products.models.Product;
import com.example.products.models.response.ProductResponse;
import com.example.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.ProductRequest;
import util.models.OrderRequest;

import java.util.List;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    @RequestMapping(value = "/products", method = RequestMethod.POST) @ResponseStatus(HttpStatus.CREATED)
    public void createProducts(@RequestBody @Valid ProductRequest productRequest){productService.createProduct(productRequest);}
    @RequestMapping(value = "/products", method = RequestMethod.GET) @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(){return productService.getProducts();}
    @RequestMapping(value = "/products/{code}", method = RequestMethod.GET) @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProducts(@Valid @PathVariable ("code") int code){return productService.getProducts(code);}
    @RequestMapping(value = "/products", method = RequestMethod.PUT)@ResponseStatus(HttpStatus.NO_CONTENT)
    public void editProducts(@Valid @RequestBody Product product){productService.editProducts(product);}
    @RequestMapping(value = "/products/{code}", method = RequestMethod.DELETE) @ResponseStatus(HttpStatus.OK)
    public void deleteProducts(@Valid @PathVariable ("code") int code){productService.deleteProducts(code);}
    @RequestMapping(value = "/products/editStock", method = RequestMethod.PUT)@ResponseStatus(HttpStatus.NO_CONTENT)
    public void editStockProducts(@Valid @RequestBody @NotNull OrderRequest orderRequest){
        productService.editStockProducts(orderRequest.getListProducts(), orderRequest.isSupplier());
    }
    @RequestMapping(value = "/products/calcTotal", method = RequestMethod.POST)@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Double> calcTotal(@Valid @RequestBody List<request.ProductRequest> productRequestList){
        return ResponseEntity.ok(productService.calcTotal(productRequestList));
    }

}
