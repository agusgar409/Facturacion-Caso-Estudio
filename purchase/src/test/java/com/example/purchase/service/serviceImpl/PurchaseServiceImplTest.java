package com.example.purchase.service.serviceImpl;

import com.example.purchase.model.response.PurchaseResponse;
import com.example.purchase.mapper.PurchaseOrderMapperController;
import com.example.purchase.model.Item;
import com.example.purchase.model.PurchaseOrder;
import com.example.purchase.model.request.PurchaseOrderRequest;
import com.example.purchase.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.web.client.RestTemplate;
import request.ProductRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class PurchaseServiceImplTest {

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    @Spy
    private PurchaseOrderMapperController mapper = Mappers.getMapper(PurchaseOrderMapperController.class);
    private static PurchaseOrder purchase =  new PurchaseOrder();
    private static PurchaseOrder purchase2 = new PurchaseOrder();
    private static List<PurchaseOrder> purchaseList =  new ArrayList<>();
    private static ProductRequest product = new ProductRequest();

    private static List<Item> list = new ArrayList<>();
    private static Item item = new Item(1L);
    private static List<ProductRequest> listProducts = new ArrayList<>();
    private static PurchaseResponse purchaseResponse = new PurchaseResponse();
    private static PurchaseOrderRequest purchaseRequest = new PurchaseOrderRequest();

    @BeforeEach
    void setUp() {
        product.setAmount(500);
        product.setId(1);
        product.setPrice(20.0);

        list.add(item);

        listProducts.add(product);
        purchase.setCustomer(1L);
        purchase.setTotal(10000.0);
        purchase.setNumber("A");
        purchase.setId(1L);
        purchase.setCategory(1);

        purchase2 = purchase;
        purchaseRequest.setCustomer(5L);
        purchaseRequest.setTotal(10000.0);
        purchaseRequest.setListProducts(listProducts);
        purchaseRequest.setCategory(1L);

        purchaseList.add(purchase);

        purchaseResponse = mapper.toPurchaseDto(purchase);
        purchase.setStatus(2L);
        MockitoAnnotations.initMocks(this);
    }
    /*
    @Test
    void calcTotal() throws Exception {
        String uri = "http://localhost:8085/products/calcTotal";
        when(restTemplate.postForObject(uri, listProducts, Double.class)).thenReturn(10000.0);
        assertEquals(10000.0, purchaseService.calcTotal(listProducts));
    }
    @Test
    void searchPurchase() {
        when(purchaseOrderRepository.getByNumber("A")).thenReturn(Optional.ofNullable(purchase));
        when(mapper.toPurchaseDto(purchase)).thenReturn(purchaseDto);
        assertEquals(purchaseDto, purchaseService.searchPurchase("A"));
    }
    @Test
    void searchAllPurchases() {
        //mapper.toPurchaseDtoList(purchaseOrderRepository.findAll());
        when(purchaseOrderRepository.findAll()).thenReturn(purchaseList);
        List<PurchaseDto> a = purchaseService.searchAllPurchases();
        assertEquals(a.get(0).getNumber(), purchaseDto.getNumber());
    }

    @Test
    void editPurchase() {
        LocalDate date = LocalDate.of(2022,7,28);
        PurchaseOrderRequest updateRequest = PurchaseOrderRequest.builder()
                .customer(7L)
                .date(date)
                .build();
        PurchaseOrder order = mapper.toPurchaseOrder(updateRequest);
        given(purchaseOrderRepository.getByNumber("A")).willReturn(Optional.ofNullable(order));
        given(purchaseOrderRepository.save(any(PurchaseOrder.class))).willReturn(order);
        purchaseService.updatePurchaseOrder("A", updateRequest);
        assertThat(purchaseService.searchPurchaseOrder("A").getCustomer()).isEqualTo(7L);
        assertThat(purchaseService.searchPurchaseOrder("A").getDate()).isEqualTo(date);
    }

    @Test
    void deletePurchase() {
        assertThrows(NotFoundException.class, () -> purchaseService.deletePurchase("B"));
        when(purchaseOrderRepository.getByNumber("A")).thenReturn(Optional.of(purchase));
        assertThrows(DeletionInvalidException.class, () -> purchaseService.deletePurchase("A"));
        purchase2.setStatus(3L);
        when(purchaseOrderRepository.getByNumber("C")).thenReturn(Optional.ofNullable(purchase2));
        assertTrue(purchaseService.deletePurchase("C"));
    }
    @Test
    void changeStatusPurchaseOrder() {
        when(purchaseOrderRepository.getByNumber("A")).thenReturn(Optional.ofNullable(purchase));
        when(purchaseOrderRepository.save(mapper.dtoToPurchaseOrder(purchaseService.searchPurchase("A")))).thenReturn(purchase);
        purchaseService.changeStatusPurchaseOrder("A", 2L);
        assertTrue(purchaseService.changeStatusPurchaseOrder("A",2L));
    }*/
}