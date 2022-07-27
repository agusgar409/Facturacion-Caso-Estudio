package com.example.Items.service.serviceImpl;

import com.example.Items.api.ApiConstants;
import com.example.Items.client.ProductClient;
import com.example.Items.mapper.ItemMapperStruct;
import com.example.Items.model.Item;
import com.example.Items.model.ItemList;
import com.example.Items.model.filter.OrderRequestFilter;
import com.example.Items.model.response.OrderResponseList;
import com.example.Items.repository.ItemsRepository;
import com.example.Items.service.ItemService;
import com.example.Items.specification.OrderSpecification;
import errors.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import request.ProductRequest;
import util.models.ItemEditStock;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final OrderSpecification specification;

    private final ProductClient productClient;
    private final ItemsRepository itemsRepository;
    private final ItemMapperStruct itemMapperStruct;

    @Override
    public List<Long> createItems(OrderRequest orderRequest) {

        List<Long> listItems = new ArrayList<>();

        this.setProveedorProperties(orderRequest);

        for (ProductRequest p : orderRequest.getListProducts()) {
            Item newItem = itemMapperStruct.orderRequest2Entity(orderRequest, p);
            newItem.setSupplier(orderRequest.isSupplier());
            newItem = itemsRepository.save(newItem);
            listItems.add(newItem.getId());
        }

        return listItems;
    }

    private void setProveedorProperties(OrderRequest orderRequest) {

        if (orderRequest.getCategory() == 2) {
            orderRequest.setSupplier(true);
        } else if (orderRequest.getCategory() == 1) {
            orderRequest.setSupplier(false);
        }

        ItemEditStock itemEditStock = itemMapperStruct.orderRequest2ItemStock(orderRequest);

        productClient.editStockProducts(itemEditStock);
    }

    @Override
    public OrderResponseList getAllOrdersByPageAndFilter(PageRequest pageRequest, OrderRequestFilter filter) {
        Specification<Item> spec = specification.getAllBySpec(filter);
        List<Item> page = itemsRepository.findAll(spec);
        ItemList itemList = new ItemList(page.stream().toList(), pageRequest, page.stream().count());
        return buildSaleOrderList(itemList);
    }

    private OrderResponseList buildSaleOrderList(ItemList itemList) {

        return OrderResponseList.builder()
                .content(itemMapperStruct.listOrderToListOrderResponse(itemList.getContent()))
                .totalPages(itemList.getTotalPages())
                .totalElements(itemList.getTotalElements())
                .nextUri(getNexPage(itemList))
                .previousUri(getPreviousPage(itemList))
                .build();
    }

    private String getPreviousPage(ItemList itemList) {
        final int nextPage = itemList.getPageable().previousOrFirst().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);

    }

    private String getNexPage(ItemList itemList) {
        final int nextPage = itemList.getPageable().next().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }

    @Override
    public Boolean editStockProducts(ItemsEditDto listProducts, boolean isSupplier) {
        List<ProductRequest> productRequestList = new ArrayList<>();
        ItemEditStock itemEditStock = new ItemEditStock();

        for (Long id : listProducts.getListItems()) {
            Item item = itemsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
            ProductRequest productRequest = itemMapperStruct.listProducts2ProductsRequest(item);
            productRequestList.add(productRequest);
            itemsRepository.delete(item);
        }

        itemEditStock.setListProducts(productRequestList);
        itemEditStock.setProveedor(isSupplier);

        productClient.editStockProducts(itemEditStock);

        return false;
    }
}
