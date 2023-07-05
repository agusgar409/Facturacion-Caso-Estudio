package com.example.purchase.application.service;


import com.example.Items.api.ApiConstants;
import com.example.purchase.domain.mapper.PurchaseOrderMapperController;
import com.example.purchase.domain.model.PurchaseOrder;
import com.example.purchase.domain.model.PurchaseOrderList;
import com.example.purchase.domain.model.request.OrderRequestFilter;
import com.example.purchase.domain.model.response.OrderResponseList;
import com.example.purchase.domain.repository.PurchaseOrderRepository;
import com.example.purchase.domain.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Pagination {
    private final PurchaseOrderMapperController mapper;
    private final OrderSpecification spec;
    private final PurchaseOrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderResponseList getAllOrdersByPageAndFilter(PageRequest pageRequest, OrderRequestFilter filter) {
        Specification<PurchaseOrder> orderSpec = spec.getAllBySpec(filter);
        Page<PurchaseOrder> page = orderRepository.findAll(orderSpec, pageRequest);
        PurchaseOrderList purchaseOrderList = new PurchaseOrderList(page.getContent(), pageRequest, page.getTotalElements());
        return buildPurchaseOrderList(purchaseOrderList);
    }

    private OrderResponseList buildPurchaseOrderList(PurchaseOrderList purchaseOrderList) {
        return OrderResponseList.builder()
                .content(mapper.listOrderToListOrderResponse(purchaseOrderList.getContent()))
                .totalPages(purchaseOrderList.getTotalPages())
                .totalElements(purchaseOrderList.getTotalElements())
                .nextUri(getNexPage(purchaseOrderList))
                .previousUri(getPreviousPage(purchaseOrderList))
                .build();
    }

    private String getPreviousPage(PurchaseOrderList saleOrderList) {
        final int nextPage = saleOrderList.getPageable().previousOrFirst().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }

    private String getNexPage(PurchaseOrderList saleOrderList) {
        final int nextPage = saleOrderList.getPageable().next().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }


}
