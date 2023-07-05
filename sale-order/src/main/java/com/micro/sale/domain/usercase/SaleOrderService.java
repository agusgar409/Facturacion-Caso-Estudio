package com.micro.sale.domain.usercase;

import com.micro.sale.domain.model.SaleOrder;
import com.micro.sale.imput.rs.request.OrderUpdateRequest;
import models.OrderRequest;

/**
 * @author jrodriguez
 */
public interface SaleOrderService {

    String saveOrder(SaleOrder order, OrderRequest inputOrder);

    SaleOrder getByNumber(String number);

    void changeStatusOrder(String number, Long status) ;

    void updateOrder(OrderUpdateRequest orderUpdate, String number);

    void deleteOrder(String number);
}
