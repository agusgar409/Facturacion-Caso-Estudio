package com.example.Items.service.serviceImpl;

import com.example.Items.mapper.ItemMapperStruct;
import com.example.Items.model.Item;
import com.example.Items.repository.ItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import request.ProductRequest;
import util.TemplateResponse;
import util.models.ItemEditStock;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

class ItemServiceImplTest {

    @Spy
    private ItemMapperStruct itemMapperStruct = Mappers.getMapper(ItemMapperStruct.class);

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private TemplateResponse templateResponse;

    @Mock
    private ItemsRepository itemsRepository;

    private OrderRequest orderRequest;

    private ProductRequest productRequest;

    private Item item;

    @BeforeEach
    void setUp() {
        productRequest = ProductRequest.builder()
                .id(1)
                .amount(5)
                .price(20D)
                .build();

        orderRequest = OrderRequest.builder()
                .category(3)
                .customer(42013929L)
                .description("prueba junit")
                .status(2L)
                .total(120D)
                .date(LocalDate.ofEpochDay(2022-8-3))
                .listProducts(Collections.singletonList(productRequest))
                .build();

        item = Item.builder().build();
    }

    @Test
    void createItems() {

        ProductRequest productRequest1 = ProductRequest.builder()
                .id(1)
                .amount(7)
                .price(200D)
                .build();

        List<ProductRequest> productRequestList = new ArrayList<>();
        productRequestList.add(productRequest1);

        ItemEditStock itemEditStock = ItemEditStock.builder()
                .listProducts(productRequestList)
                .isProveedor(true)
                .build();

        when(itemMapperStruct.orderRequest2ItemStock(orderRequest)).thenReturn(itemEditStock);

        when(itemMapperStruct.orderRequest2Entity(orderRequest,productRequest)).thenReturn(item);
        when(itemsRepository.save(item)).thenReturn(item);

        List<Long> listId = itemService.createItems(orderRequest);
    }
}