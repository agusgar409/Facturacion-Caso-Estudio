package com.example.Items.mapper;


import com.example.Items.model.Item;

import com.example.Items.model.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import request.ProductRequest;
import util.models.ItemEditStock;
import util.models.OrderRequest;

import java.util.List;

@Mapper
public interface ItemMapperStruct {
    @Mapping(target = "product",source = "p.id")
    Item orderRequest2Entity(OrderRequest orderRequest, ProductRequest p);

    List<OrderResponse> listOrderToListOrderResponse(List<Item> content);

    @Mapping(target = "id",source = "product")
    ProductRequest listProducts2ProductsRequest(Item item);

    ItemEditStock orderRequest2ItemStock(OrderRequest orderRequest);
}
