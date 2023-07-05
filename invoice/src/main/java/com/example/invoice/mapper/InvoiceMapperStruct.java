package com.example.invoice.mapper;

import com.example.invoice.dto.InvoiceDto;
import com.example.invoice.dto.InvoiceUpdate;
import com.example.invoice.model.InvoiceEntity;
import com.example.invoice.model.response.OrderResponse;
import models.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface InvoiceMapperStruct {

    InvoiceEntity inputOrder2InvoiceEntity(OrderRequest inputOrder);

    InvoiceDto invoiceEntity2InvoiceDto(InvoiceEntity invoiceEntity);

    List<OrderResponse> listOrderToListOrderResponse(List<InvoiceEntity> content);

    InvoiceEntity updateInvoice(Long newStatus);
}
