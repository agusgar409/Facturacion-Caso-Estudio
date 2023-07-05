package com.example.purchase.domain.mapper;

import com.example.purchase.domain.model.response.PurchaseResponse;
import com.example.purchase.domain.model.PurchaseOrder;
import org.mapstruct.Mapper;
import models.OrderRequest;

import java.util.List;

@Mapper
public interface PurchaseOrderMapperController {
    PurchaseResponse toPurchaseDto(PurchaseOrder purchaseOrder);
    PurchaseOrder toPurchaseOrder(OrderRequest request);
    List<PurchaseResponse> toPurchaseDtoList(List<PurchaseOrder>  purchaseOrder);
    List<PurchaseResponse> listOrderToListOrderResponse(List<PurchaseOrder> content);


}
