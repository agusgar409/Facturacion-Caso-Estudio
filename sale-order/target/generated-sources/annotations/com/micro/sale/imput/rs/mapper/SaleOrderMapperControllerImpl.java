package com.micro.sale.imput.rs.mapper;

import com.micro.sale.domain.model.SaleOrder;
import com.micro.sale.domain.model.SaleOrder.SaleOrderBuilder;
import com.micro.sale.domain.model.Status;
import com.micro.sale.imput.rs.request.OrderUpdateRequest;
import com.micro.sale.imput.rs.response.OrderResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import models.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class SaleOrderMapperControllerImpl implements SaleOrderMapperController {

    @Autowired
    private StatusMapperController statusMapperController;

    @Override
    public List<OrderResponse> listOrderToListOrderResponse(List<SaleOrder> content) {
        if ( content == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( content.size() );
        for ( SaleOrder saleOrder : content ) {
            list.add( toOrderResponse( saleOrder ) );
        }

        return list;
    }

    @Override
    public OrderResponse toOrderResponse(SaleOrder order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setStatus( statusMapperController.toStatusResponse( order.getStatus() ) );
        orderResponse.setNumber( order.getNumber() );
        orderResponse.setCustomer( order.getCustomer() );
        orderResponse.setDate( order.getDate() );
        if ( order.getIdCategory() != null ) {
            orderResponse.setIdCategory( order.getIdCategory().longValue() );
        }
        orderResponse.setTotal( order.getTotal() );

        return orderResponse;
    }

    @Override
    public SaleOrder toSaleOrder(OrderRequest request) {
        if ( request == null ) {
            return null;
        }

        SaleOrderBuilder saleOrder = SaleOrder.builder();

        saleOrder.status( orderRequestToStatus( request ) );
        saleOrder.idCategory( request.getCategory() );
        saleOrder.customer( request.getCustomer() );
        saleOrder.date( request.getDate() );
        saleOrder.total( request.getTotal() );

        return saleOrder.build();
    }

    @Override
    public SaleOrder orderUpdateToSaleOrder(OrderUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        SaleOrderBuilder saleOrder = SaleOrder.builder();

        saleOrder.customer( request.getCustomer() );
        saleOrder.date( request.getDate() );

        return saleOrder.build();
    }

    protected Status orderRequestToStatus(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        Status status = new Status();

        status.setId( orderRequest.getStatus() );

        return status;
    }
}
