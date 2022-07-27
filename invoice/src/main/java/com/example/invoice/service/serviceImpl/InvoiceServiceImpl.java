package com.example.invoice.service.serviceImpl;

import com.example.invoice.api.ApiConstants;
import com.example.invoice.client.ItemClient;
import com.example.invoice.client.ProductClient;
import com.example.invoice.dto.InvoiceDto;
import com.example.invoice.mapper.InvoiceMapperStruct;
import com.example.invoice.model.*;
import com.example.invoice.model.filter.OrderRequestFilter;
import com.example.invoice.model.response.OrderResponseList;
import com.example.invoice.repository.InvoiceItemRepository;
import com.example.invoice.repository.InvoiceRepository;
import com.example.invoice.service.InvoiceService;
import com.example.invoice.specification.OrderSpecification;
import errors.DeletionInvalidException;
import errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.NumberGenerator;
import util.TemplateResponse;
import util.models.ItemResponse;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceMapperStruct invoiceMapperStruct;

    private final InvoiceRepository invoiceRepository;

    private final InvoiceItemRepository invoiceItemRepository;

    private final NumberGenerator generator;

    private final ProductClient productClient;

    private final ItemClient itemClient;

    private final OrderSpecification specification;

    @Override
    public InvoiceDto createOrderRequest(OrderRequest inputOrder) {

        InvoiceEntity invoiceEntity = invoiceMapperStruct.inputOrder2InvoiceEntity(inputOrder);

        if(inputOrder.getStatus() == 2){
            throw new DeletionInvalidException(inputOrder.getCustomer());
        }

        invoiceEntity.setNumber(generator.generateNumberOrder(invoiceEntity.getCategory(), invoiceRepository.getLast()));
        invoiceEntity.setTotal(productClient.calcTotal(inputOrder.getListProducts()));
        invoiceEntity = invoiceRepository.save(invoiceEntity);
        addInvoiceItems(inputOrder,invoiceEntity.getIdInvoice());

        return invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntity);
    }

    private void addInvoiceItems(OrderRequest inputOrder, Long idInvoice) {

        Long[] idItems = itemClient.newItem(inputOrder).getBody().toArray(new Long[0]);
        List<ItemResponse> setItems = Arrays.stream(idItems).map(ItemResponse::new).toList();

        setItems.forEach(itemResponse -> {
            InvoiceItems invoiceItems = InvoiceItems.builder()
                    .idInvoice(idInvoice)
                    .idItem(itemResponse.getId())
                    .build();
            invoiceItemRepository.save(invoiceItems);
        });
    }

    @Override
    public Boolean deleteInvoice(String number) {
        InvoiceEntity invoiceEntity = getInvoiceNumber(number);

        invoiceItemRepository.findByIdInvoice(invoiceEntity.getIdInvoice()).forEach(invoiceItemRepository::delete);
        invoiceRepository.deleteById(invoiceEntity.getIdInvoice());
        return true;

    }

    @Override
    public OrderResponseList getAllOrdersByPageAndFilter(PageRequest pageRequest, OrderRequestFilter filter) {
        Specification<InvoiceEntity> spec = specification.getAllBySpec(filter);
        List<InvoiceEntity> page = invoiceRepository.findAll(spec);
        InvoiceEntityList invoiceOrderList = new InvoiceEntityList(page.stream().toList(), pageRequest, page.stream().count());
        return buildSaleOrderList(invoiceOrderList);
    }

    @Override
    public InvoiceDto updateInvoice(String number, Long status) {

        InvoiceEntity invoiceEntity = getInvoiceNumber(number);
        invoiceEntity.setStatus(status);
        invoiceEntity = invoiceRepository.save(invoiceEntity);

        return invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntity);
    }
    @Override
    public InvoiceEntity getInvoiceNumber(String number) {
        return invoiceRepository.getByNumber(number).orElseThrow(() -> new NotFoundException(number));
    }

    private OrderResponseList buildSaleOrderList(InvoiceEntityList invoiceOrderList) {
        return OrderResponseList.builder()
                .content(invoiceMapperStruct.listOrderToListOrderResponse(invoiceOrderList.getContent()))
                .totalPages(invoiceOrderList.getTotalPages())
                .totalElements(invoiceOrderList.getTotalElements())
                .nextUri(getNexPage(invoiceOrderList))
                .previousUri(getPreviousPage(invoiceOrderList))
                .build();
    }

    private String getPreviousPage(InvoiceEntityList saleOrderList) {
        final int nextPage = saleOrderList.getPageable().previousOrFirst().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }

    private String getNexPage(InvoiceEntityList saleOrderList) {
        final int nextPage = saleOrderList.getPageable().next().getPageNumber();
        return ApiConstants.uriByPageAsString.apply(nextPage);
    }
}
