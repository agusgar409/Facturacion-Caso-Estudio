package com.example.products.domain.service;

import com.example.products.domain.models.Product;
import com.example.products.imput.rs.response.ProductResponse;
import org.springframework.stereotype.Service;
import models.ProductRequest;

import java.util.List;

@Service
public interface ProductService {

    void editStockProducts(List<ProductRequest> listOperation, boolean isProveedor);
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getProducts();
    ProductResponse getProducts(int code);
    void editProducts(Product product);
    void deleteProducts(int code);
    Double calcTotal(List<ProductRequest> productRequestList);
}
