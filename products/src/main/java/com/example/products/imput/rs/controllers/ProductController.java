package com.example.products.imput.rs.controllers;

import com.example.products.domain.service.ProductService;
import com.example.products.imput.rs.mapper.ProductMapper;
import com.example.products.imput.rs.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import models.ProductRequest;
import models.ItemEditStock;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;


    @PostMapping(value = "/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProducts(@RequestBody @Valid ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }


    @GetMapping(value = "/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = "/products/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProducts(@Valid @PathVariable("code") int code) {
        return productService.getProducts(code);
    }

    @PutMapping(value = "/products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editProducts(@Valid @RequestBody ProductRequest request) {
        productService.editProducts(mapper.toProduct(request));
    }

    @DeleteMapping(value = "/products/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducts(@Valid @PathVariable("code") int code) {
        productService.deleteProducts(code);
    }

    @PutMapping(value = "/products/editStock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editStockProducts(@Valid @RequestBody ItemEditStock orderRequest) {
        productService.editStockProducts(orderRequest.getListProducts(), orderRequest.isSupplier());
    }

    @PostMapping("/products/calcTotal")
    @ResponseStatus(HttpStatus.OK)
    public Double calcTotal(@RequestBody List<ProductRequest> productRequestList) {
        return productService.calcTotal(productRequestList);
    }

}
