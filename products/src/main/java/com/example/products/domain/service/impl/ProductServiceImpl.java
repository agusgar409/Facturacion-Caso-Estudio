package com.example.products.domain.service.impl;

import com.example.products.common.exception.errors.NoStockException;
import com.example.products.common.exception.errors.ProductNotFoundException;
import com.example.products.domain.models.Product;
import com.example.products.domain.repository.ProductRepository;
import com.example.products.domain.service.ProductService;
import com.example.products.imput.rs.mapper.ProductMapper;
import com.example.products.imput.rs.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import models.ProductRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Maximo Di Napoli
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public void createProduct(ProductRequest productRequest) {
        saveProduct(mapper.toProduct(productRequest));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProducts() {
        return mapper.productToProductResponseList(productRepository.findAll());
    }

    private void saveProduct(Product product) {
        productRepository.save(product);
    }


    @Override
    public ProductResponse getProducts(int id) {
        return mapper.productToProductResponse(getProductById(id));
    }


    @Override
    public void deleteProducts(int code) {
        getProductById(code);
        deleteProduct(code);
    }

    private void deleteProduct(int code) {
        productRepository.deleteById(code);
    }

    @Override
    public Double calcTotal(List<ProductRequest> productRequestList) {
        AtomicReference<Double> totalPrice = new AtomicReference<>(0D);
        productRequestList.forEach(p -> totalPrice.updateAndGet(v -> v + p.getPrice() * p.getAmount()));
        return totalPrice.get();
    }

    @Override
    public void editStockProducts(List<ProductRequest> list, boolean isProveedor) {
        validateOperation(list, isProveedor);
        list.forEach(p -> {
            Product product = getProductById(p.getId());
            product.setAmount(product.getAmount() + p.getAmount());
            editProducts(product);
        });
    }

    private void validateOperation(@NotNull List<ProductRequest> list, boolean isProveedor) {
        list.forEach(p -> {
            if (!isProveedor) p.setAmount(p.getAmount() * -1);
            validateStock(getProductById(p.getId()), p);
        });
    }

    private void validateStock(@NotNull Product product, @NotNull ProductRequest operation) {
        if ((product.getAmount() + operation.getAmount()) < 0) {
            throw new NoStockException(product.getId());
        }
    }

    @Override
    public void editProducts(Product product) {
        getProductById(product.getId());
        saveProduct(product);
    }

    private Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

}
