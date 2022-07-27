package com.example.purchase.service;

import com.example.purchase.model.response.PurchaseResponse;
import com.example.purchase.model.request.PurchaseOrderRequest;
import org.springframework.transaction.annotation.Transactional;
import util.models.OrderRequest;

import java.util.List;

public interface PurchaseService {
    PurchaseResponse searchPurchase(String id);
    List<PurchaseResponse> searchAllPurchases();
    void updateOrder(String number, PurchaseOrderRequest purchaseOrderRequest);
    void generateOrder(OrderRequest inputOrder);
    @Transactional
    void changeStatusOrder(String number, Long status);
    void deletePurchaseOrder(String id);
}