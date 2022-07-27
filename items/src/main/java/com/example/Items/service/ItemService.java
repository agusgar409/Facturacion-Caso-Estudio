package com.example.Items.service;

import com.example.Items.model.filter.OrderRequestFilter;
import com.example.Items.model.response.OrderResponseList;
import org.springframework.data.domain.PageRequest;
import util.models.ItemEditStock;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import java.util.List;

public interface ItemService {
    List<Long> createItems(OrderRequest orderRequest);

    OrderResponseList getAllOrdersByPageAndFilter(PageRequest of, OrderRequestFilter filter);

    Boolean editStockProducts(ItemsEditDto listItems, boolean isSupplier);
}
