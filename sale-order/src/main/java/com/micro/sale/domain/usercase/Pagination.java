package com.micro.sale.domain.usercase;

import com.micro.sale.domain.model.SaleOrder;
import com.micro.sale.domain.model.SaleOrderList;
import com.micro.sale.domain.repository.SaleOrderRepository;
import com.micro.sale.imput.rs.api.ApiConstants;
import com.micro.sale.imput.rs.mapper.SaleOrderMapperController;
import com.micro.sale.imput.rs.request.OrderRequestFilter;
import com.micro.sale.imput.rs.response.OrderResponseList;
import com.micro.sale.imput.rs.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author jrodriguez
 */
@Service
@RequiredArgsConstructor
public class Pagination {
    private final SaleOrderMapperController mapper;
    private final OrderSpecification spec;
    private final SaleOrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderResponseList getAllOrdersByPageAndFilter(PageRequest pageRequest, OrderRequestFilter filter) {
        Specification<SaleOrder> orderSpec = spec.getAllBySpec(filter);
        Page<SaleOrder> page = orderRepository.findAll(orderSpec, pageRequest);
        SaleOrderList saleOrderList = new SaleOrderList(page.getContent(), pageRequest, page.getTotalElements());
        return buildSaleOrderList(saleOrderList);
    }

    private OrderResponseList buildSaleOrderList(SaleOrderList saleOrderList) {
        return OrderResponseList.builder()
                .content(mapper.listOrderToListOrderResponse(saleOrderList.getContent()))
                .totalPages(saleOrderList.getTotalPages())
                .totalElements(saleOrderList.getTotalElements())
                .nextUri(getNexPage(saleOrderList))
                .previousUri(getPreviousPage(saleOrderList))
                .build();
    }

    private String getPreviousPage(SaleOrderList saleOrderList) {
        final int nextPage = saleOrderList.getPageable().previousOrFirst().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }

    private String getNexPage(SaleOrderList saleOrderList) {
        final int nextPage = saleOrderList.getPageable().next().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }


}
