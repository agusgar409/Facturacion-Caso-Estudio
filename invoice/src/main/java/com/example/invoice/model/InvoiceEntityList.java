package com.example.invoice.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jrodriguez
 */
public class InvoiceEntityList extends PageImpl<InvoiceEntity> {
    public InvoiceEntityList(List<InvoiceEntity> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
