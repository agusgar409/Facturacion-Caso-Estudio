package com.example.products.imput.rs.mapper;

import com.example.products.domain.models.Product;
import com.example.products.imput.rs.response.ProductResponse;
import com.example.products.imput.rs.response.ProductResponse.ProductResponseBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import models.ProductRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductRequest productRequest) {
        if ( productRequest == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productRequest.getId() );
        product.setName( productRequest.getName() );
        product.setDescription( productRequest.getDescription() );
        product.setAmount( productRequest.getAmount() );
        product.setPrice( productRequest.getPrice() );

        return product;
    }

    @Override
    public ProductResponse productToProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.amount( product.getAmount() );
        productResponse.price( product.getPrice() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );

        return productResponse.build();
    }

    @Override
    public List<ProductResponse> productToProductResponseList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( products.size() );
        for ( Product product : products ) {
            list.add( productToProductResponse( product ) );
        }

        return list;
    }
}
