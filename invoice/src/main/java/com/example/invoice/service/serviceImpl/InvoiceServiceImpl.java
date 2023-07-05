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
import errors.CategoryInvalidException;
import errors.NotFoundException;
import errors.StatusInvalidException;
import lombok.RequiredArgsConstructor;
import models.ItemResponse;
import models.OrderRequest;
import models.ProductRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.NumberGenerator;

import java.util.Arrays;
import java.util.List;

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
    public InvoiceDto createOrderRequest(OrderRequest inputOrder) throws CategoryInvalidException, StatusInvalidException {

        InvoiceEntity invoiceEntity = invoiceMapperStruct.inputOrder2InvoiceEntity(inputOrder);

        if(inputOrder.getCategory() != 3){
            throw new CategoryInvalidException(inputOrder.getCategory());
        }else if(inputOrder.getStatus() == 2){
            throw new StatusInvalidException(inputOrder.getStatus());
        }


        invoiceEntity.setNumber(generator.generateNumberOrder(invoiceEntity.getCategory(), invoiceRepository.getLast()));

        calculateTotalProducts(inputOrder,invoiceEntity);

        invoiceEntity = invoiceRepository.save(invoiceEntity);

        addInvoiceItems(inputOrder,invoiceEntity.getIdInvoice());

        return invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntity);
    }

    private void calculateTotalProducts(OrderRequest inputOrder, InvoiceEntity invoiceEntity) {

        for(ProductRequest product: inputOrder.getListProducts()){
            productClient.getProducts(product.getId());
        }
        invoiceEntity.setTotal(productClient.calcTotal(inputOrder.getListProducts()));
    }

    private void addInvoiceItems(OrderRequest inputOrder, Long idInvoice) {

        List<Long> response = itemClient.newItem(inputOrder).getBody();


        if(response.isEmpty()){
            throw new NotFoundException(inputOrder);
        }
        Long[] idItems = response.toArray(new Long[0]);


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
        return buildInvoiceOrderList(invoiceOrderList);
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

    private OrderResponseList buildInvoiceOrderList(InvoiceEntityList invoiceOrderList) {
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
