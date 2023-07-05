package com.micro.sale.imput.rs.mapper;

import com.micro.sale.domain.model.SaleOrder;
import com.micro.sale.imput.rs.request.OrderUpdateRequest;
import com.micro.sale.imput.rs.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import models.OrderRequest;

import java.util.List;

/**
 * @author jrodriguez
 */
@Mapper(componentModel = "spring", uses = {StatusMapperController.class})
public interface SaleOrderMapperController {

    List<OrderResponse> listOrderToListOrderResponse(List<SaleOrder> content);

    @Mapping(source = "status", target = "status")
    OrderResponse toOrderResponse(SaleOrder order);

    @Mapping(source = "category", target = "idCategory")
    @Mapping(source = "status", target = "status.id")
    SaleOrder toSaleOrder(OrderRequest request);


    SaleOrder orderUpdateToSaleOrder(OrderUpdateRequest request);
}
