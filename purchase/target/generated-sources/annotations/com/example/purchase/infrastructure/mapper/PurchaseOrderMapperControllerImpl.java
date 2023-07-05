package com.example.purchase.infrastructure.mapper;

import com.example.purchase.domain.model.PurchaseOrder;
import com.example.purchase.domain.model.PurchaseOrder.PurchaseOrderBuilder;
import com.example.purchase.domain.model.response.PurchaseResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import models.OrderRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class PurchaseOrderMapperControllerImpl implements PurchaseOrderMapperController {

    @Override
    public PurchaseResponse toPurchaseDto(PurchaseOrder purchaseOrder) {
        if ( purchaseOrder == null ) {
            return null;
        }

        PurchaseResponse purchaseResponse = new PurchaseResponse();

        purchaseResponse.setNumber( purchaseOrder.getNumber() );
        purchaseResponse.setCustomer( purchaseOrder.getCustomer() );
        if ( purchaseOrder.getCategory() != null ) {
            purchaseResponse.setCategory( purchaseOrder.getCategory().longValue() );
        }
        purchaseResponse.setTotal( purchaseOrder.getTotal() );
        purchaseResponse.setDate( purchaseOrder.getDate() );
        purchaseResponse.setStatus( purchaseOrder.getStatus() );

        return purchaseResponse;
    }

    @Override
    public PurchaseOrder toPurchaseOrder(OrderRequest request) {
        if ( request == null ) {
            return null;
        }

        PurchaseOrderBuilder purchaseOrder = PurchaseOrder.builder();

        purchaseOrder.customer( request.getCustomer() );
        purchaseOrder.date( request.getDate() );
        purchaseOrder.status( request.getStatus() );
        purchaseOrder.category( request.getCategory() );
        purchaseOrder.total( request.getTotal() );

        return purchaseOrder.build();
    }

    @Override
    public List<PurchaseResponse> toPurchaseDtoList(List<PurchaseOrder> purchaseOrder) {
        if ( purchaseOrder == null ) {
            return null;
        }

        List<PurchaseResponse> list = new ArrayList<PurchaseResponse>( purchaseOrder.size() );
        for ( PurchaseOrder purchaseOrder1 : purchaseOrder ) {
            list.add( toPurchaseDto( purchaseOrder1 ) );
        }

        return list;
    }
}
