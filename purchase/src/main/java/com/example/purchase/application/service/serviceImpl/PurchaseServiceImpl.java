package com.example.purchase.application.service.serviceImpl;

import com.example.purchase.application.service.PurchaseService;
import com.example.purchase.domain.clients.ItemClient;
import com.example.purchase.domain.clients.ProductClient;
import com.example.purchase.domain.model.response.PurchaseResponse;

import com.example.purchase.domain.mapper.PurchaseOrderMapperController;
import com.example.purchase.domain.model.PurchaseOrder;
import com.example.purchase.domain.model.PurchaseOrderItem;
import com.example.purchase.domain.model.request.PurchaseOrderRequest;
import com.example.purchase.domain.repository.PurchaseOrderItemRepository;
import com.example.purchase.domain.repository.PurchaseOrderRepository;
import errors.DeletionInvalidException;
import errors.InvalidStatusChanceException;
import errors.NotFoundException;
import errors.StatusInvalidException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import models.ProductRequest;
import util.NumberGenerator;
import models.ItemsEditDto;
import models.OrderRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final ProductClient productClient;
    private final ItemClient itemClient;
    private final NumberGenerator generator;
    private final PurchaseOrderMapperController mapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository repoOrderItem;





    @Override
    public void generateOrder(OrderRequest purchaseOrderRequest) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.save(purchaseOrderRequestToPurchaseOrder(purchaseOrderRequest));
        createOrderItems(purchaseOrderRequest, purchaseOrder.getId());
    }
    @Transactional(readOnly = true) @Override
    public PurchaseResponse searchPurchase(String id) {return mapper.toPurchaseDto(searchPurchaseOrder(id));}
    @Override
    public List<PurchaseResponse> searchAllPurchases() {return mapper.toPurchaseDtoList(purchaseOrderRepository.findAll());}
    @Override
    public void updateOrder(String number, @NotNull PurchaseOrderRequest purchaseOrderRequest) {
        PurchaseOrder order = searchPurchaseOrder(number);
        Optional.ofNullable(purchaseOrderRequest.getCustomer()).ifPresent(order::setCustomer);
        Optional.ofNullable(purchaseOrderRequest.getDate()).ifPresent(order::setDate);
        purchaseOrderRepository.save(order);
    }
    @Transactional @Override
    public void changeStatusOrder(String number, Long status) {
        PurchaseOrder order = searchPurchaseOrder(number);
        validateStatusChange(order, status);
        order.setStatus(status);
        purchaseOrderRepository.save(order);
    }
    @Override
    public void deletePurchaseOrder(String number) {
        PurchaseOrder purchaseOrder = searchPurchaseOrder(number);
        if(isDraft(purchaseOrder)){throw new DeletionInvalidException(2);}
        rollBackStock(purchaseOrder);
        deletePurchaseOrderAndItemsAssociated(purchaseOrder);
    }








    private String setNumber(@NotNull PurchaseOrder order) {
        String number = generator.generateNumberOrder(order.getCategory(), purchaseOrderRepository.getLast());
        order.setNumber(number);
        return number;
    }
    private Double calcTotal(List<ProductRequest> listProducts) {
        return productClient.calcTotal(listProducts).getBody();
    }
    private long[] getItemsId(OrderRequest inputOrder) {
        List<Long> idItems = itemClient.newItem(inputOrder).getBody();
        return idItems.stream().mapToLong(l -> l).toArray();
    }
    private PurchaseOrder searchPurchaseOrder(String id) {
        return purchaseOrderRepository.getByNumber(id).orElseThrow(() -> new NotFoundException(id));
    }
    private void validateStatusChange(PurchaseOrder order, @NotNull Long requestStatus){
        switch (requestStatus.intValue()){
            case 1:
            case 2:
                if (isRefunded(order))throw new InvalidStatusChanceException(order.getStatus());
                break;
            case 3: if (isDraft(order))throw new InvalidStatusChanceException(order.getStatus());
                rollBackStock(order);
                break;
            default: throw new InvalidStatusChanceException(order.getStatus());
        }
    }
    private void rollBackStock(@NotNull PurchaseOrder purchaseOrder){
        List<Long> ids = repoOrderItem.findByIdOrder(purchaseOrder.getId()).stream().map(PurchaseOrderItem::getIdItem).collect(Collectors.toList());
        itemClient.editStockProducts(ItemsEditDto.builder().listItems(ids).build(), true);
    }
    private void createOrderItems(OrderRequest purchaseOrderRequest, Long idOrder){
        Arrays.stream(getItemsId(purchaseOrderRequest)).forEach(idItem -> {
            PurchaseOrderItem orderItem = PurchaseOrderItem.builder()
                    .idOrder(idOrder)
                    .idItem(idItem)
                    .build();
            repoOrderItem.save(orderItem);
        });
    }
    private PurchaseOrder purchaseOrderRequestToPurchaseOrder(@NotNull OrderRequest purchaseOrderRequest){
        purchaseOrderRequest.setTotal(calcTotal(purchaseOrderRequest.getListProducts()));
        PurchaseOrder purchaseOrder = mapper.toPurchaseOrder(purchaseOrderRequest);
        if (isRefunded(purchaseOrder)) throw new StatusInvalidException(purchaseOrderRequest.getStatus());
        setNumber(purchaseOrder);
        return purchaseOrder;
    }
    private boolean isDraft(@NotNull PurchaseOrder purchaseOrder){
        if(purchaseOrder.getStatus() == 2)return true;
        return false;
    }
    private boolean isRefunded(@NotNull PurchaseOrder purchaseOrder){
        if(purchaseOrder.getStatus() == 3)return true;
        return false;
    }
    private void deletePurchaseOrderAndItemsAssociated(@NotNull PurchaseOrder purchaseOrder){
        repoOrderItem.findByIdOrder(purchaseOrder.getId()).forEach(repoOrderItem::delete);
        purchaseOrderRepository.deleteById(purchaseOrder.getId());
    }
}
