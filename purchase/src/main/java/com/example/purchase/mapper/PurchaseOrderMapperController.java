package com.example.purchase.mapper;

import com.example.purchase.model.response.PurchaseResponse;
import com.example.purchase.model.PurchaseOrder;
import org.mapstruct.Mapper;
import util.models.OrderRequest;

import java.util.List;

@Mapper
public interface PurchaseOrderMapperController {
    PurchaseResponse toPurchaseDto(PurchaseOrder purchaseOrder);
    PurchaseOrder toPurchaseOrder(OrderRequest request);
    List<PurchaseResponse> toPurchaseDtoList(List<PurchaseOrder>  purchaseOrder);
}
