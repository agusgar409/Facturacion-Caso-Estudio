package com.example.products.models.mapper;

import com.example.products.models.Product;
import com.example.products.models.response.ProductResponse;
import org.mapstruct.Mapper;
import request.ProductRequest;

import java.util.List;

@Mapper
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    ProductResponse productToProductResponse(Product product);

    List<ProductResponse> productToProductResponseList(List<Product> products);
}
