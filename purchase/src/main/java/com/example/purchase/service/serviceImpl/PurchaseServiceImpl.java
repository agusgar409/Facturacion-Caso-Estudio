package com.example.purchase.service.serviceImpl;

import com.example.purchase.clients.ItemClient;
import com.example.purchase.clients.ProductClient;
import com.example.purchase.model.response.PurchaseResponse;

import com.example.purchase.service.PurchaseService;
import com.example.purchase.mapper.PurchaseOrderMapperController;
import com.example.purchase.model.PurchaseOrder;
import com.example.purchase.model.PurchaseOrderItem;
import com.example.purchase.model.request.PurchaseOrderRequest;
import com.example.purchase.repository.PurchaseOrderItemRepository;
import com.example.purchase.repository.PurchaseOrderRepository;
import errors.DeletionInvalidException;
import errors.InvalidStatusException;
import errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.ProductRequest;
import util.NumberGenerator;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

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
            case 1: if (isRefunded(order))throw new InvalidStatusException(" You cannot confirm an order already refunded ");
                break;
            case 2: if (isRefunded(order))throw new InvalidStatusException(" You can not refund a draft order ");
                break;
            case 3: if (isDraft(order))throw new InvalidStatusException(" You can not make a refund of a draft order ");
                rollBackStock(order);
                break;
            default: throw new InvalidStatusException("Invalid status");
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
        if (isRefunded(purchaseOrder)) throw new InvalidStatusException("Invalid status");
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
