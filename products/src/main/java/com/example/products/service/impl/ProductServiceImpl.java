package com.example.products.service.impl;

import com.example.products.exception.errors.NoStockException;
import com.example.products.exception.errors.ProductNotFoundException;
import com.example.products.models.Product;
import com.example.products.models.mapper.ProductMapper;
import com.example.products.models.response.ProductResponse;
import com.example.products.repository.ProductRepository;
import com.example.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import request.ProductRequest;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper mapper;


    private void validateStock(@NotNull Product product, @NotNull ProductRequest operation) {
        if ((product.getAmount() + operation.getAmount()) < 0) {
            throw new NoStockException(product.getId());
        }
    }

    private boolean validateOperation(@NotNull List<ProductRequest> listOperation, boolean isProveedor) {
        for (ProductRequest operation : listOperation) {
            if (!isProveedor) {
                operation.setAmount(operation.getAmount() * -1);
            }
            validateStock(getProductById(operation.getId()), operation);
        }
        return true;
    }

    private Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    private void saveProduct(Product product) {
        productRepository.save(product);
    }

    private void deleteProduct(int code) {
        productRepository.deleteById(code);
    }


    @Override
    public void createProduct(ProductRequest productRequest) {
        saveProduct(mapper.toProduct(productRequest));
    }

    @Override
    public List<ProductResponse> getProducts() {
        return mapper.productToProductResponseList(productRepository.findAll());
    }

    @Override
    public ProductResponse getProducts(int id) {
        return mapper.productToProductResponse(getProductById(id));
    }

    @Override
    public void editProducts(Product product) {
        getProductById(product.getId());
        saveProduct(product);
    }

    @Override
    public void deleteProducts(int code) {
        getProductById(code);
        deleteProduct(code);
    }

    @Override
    public Double calcTotal(@NotNull List<ProductRequest> productRequestList) {
        Double totalPrice = 0D;
        for (ProductRequest p : productRequestList) {
            totalPrice += p.getPrice() * p.getAmount();
        }
        return totalPrice;
    }

    @Override
    public void editStockProducts(List<ProductRequest> listOperation, boolean isProveedor) {
        validateOperation(listOperation, isProveedor);
        for (ProductRequest operation : listOperation) {
            Product product = getProductById(operation.getId());
            product.setAmount(product.getAmount() + operation.getAmount());
            editProducts(product);
        }
    }
}
