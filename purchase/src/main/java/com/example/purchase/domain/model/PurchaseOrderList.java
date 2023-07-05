package com.example.purchase.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PurchaseOrderList extends PageImpl<PurchaseOrder> {
    public PurchaseOrderList(List<PurchaseOrder> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
