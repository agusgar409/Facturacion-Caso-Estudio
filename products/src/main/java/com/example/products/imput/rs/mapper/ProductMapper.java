package com.example.products.imput.rs.mapper;

import com.example.products.domain.models.Product;
import com.example.products.imput.rs.response.ProductResponse;
import org.mapstruct.Mapper;
import models.ProductRequest;

import java.util.List;

@Mapper
public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);

    ProductResponse productToProductResponse(Product product);



    List<ProductResponse> productToProductResponseList(List<Product> products);
}
