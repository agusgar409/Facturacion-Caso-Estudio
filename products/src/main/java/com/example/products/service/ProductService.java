package com.example.products.service;

import com.example.products.models.Product;
import com.example.products.models.response.ProductResponse;
import org.springframework.stereotype.Service;
import request.ProductRequest;

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
