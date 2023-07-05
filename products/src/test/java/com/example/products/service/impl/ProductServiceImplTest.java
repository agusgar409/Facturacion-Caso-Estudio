package com.example.products.service.impl;

import com.example.products.domain.models.Product;
import com.example.products.domain.repository.ProductRepository;
import com.example.products.domain.service.impl.ProductServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import static org.mockito.ArgumentMatchers.any;

class staticProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private static Product product;
    private static Product product2;

    /*
    @BeforeEach
    void setUp() {
        product = new Product();
        product.setCode(1);
        product.setName("Cocacola");
        product.setDescription("Bebida popular");
        product.setPrice(130.0F);
        product.setStockQuantity(1000);
        //product2 = new Product();
        product2 = product;
        product2.setName("AAAAAAAA");
        product2.setCode(1);

        operation.setAmount(100);
        operation.setId(1);

        operation2.setAmount(10000);
        operation2.setId(1);

        operation3.setAmount(100);
        operation3.setId(5);

        MockitoAnnotations.initMocks(this);

    }
    @Test
    void getProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        assertNotNull(productService.getProducts());
    }
    @Test
    void getProduct(){
        when(productRepository.existsById(1)).thenReturn(true);
        when(productRepository.existsById(2)).thenReturn(false);
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product));
        Assertions.assertEquals(product, productService.getProducts(1));
        assertThrows(ProductNotFoundException.class, () -> productService.getProducts(2));
    }
    @Test
    void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        assertTrue(productService.createProduct(new Product()));
        when(productRepository.existsById(1)).thenReturn(true);
        assertThrows(CodeAlreadyUsedException.class, () -> productService.createProduct(product));
    }
    @Test
    void deleteProducts() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProducts(2));
    }
    @Test
    void editProducts() {
        when(productRepository.existsById(1)).thenReturn(true);
        assertTrue(productService.editProducts(product2));
        product2.setCode(2);
        assertThrows(ProductNotFoundException.class, () -> productService.editProducts(product2));
    }
    @Test
    void editStockProducts() {

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.existsById(1)).thenReturn(true);


        assertTrue(productService.editStockProducts(purchasesList, false));

        purchasesList.remove(operation);
        purchasesList.add(operation2);

        assertThrows(NoStockException.class, () -> productService.editStockProducts(purchasesList, false));

        purchasesList.remove(operation2);
        purchasesList.add(operation3);

        assertThrows(ProductNotFoundException.class, () -> productService.editStockProducts(purchasesList, false));

    }

    */
}