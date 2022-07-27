package com.micro.sale.domain.usercase;

import com.micro.sale.domain.model.SaleOrder;
import com.micro.sale.domain.model.SaleOrderItem;
import com.micro.sale.domain.model.Status;
import com.micro.sale.domain.model.StatusEnum;
import com.micro.sale.domain.repository.SaleOrderItemRepository;
import com.micro.sale.domain.repository.SaleOrderRepository;
import com.micro.sale.domain.repository.StatusRepository;
import com.micro.sale.imput.rs.clients.ItemClient;
import com.micro.sale.imput.rs.clients.ProductClient;
import com.micro.sale.imput.rs.request.OrderUpdateRequest;
import errors.DeletionInvalidException;
import errors.InvalidStatusChanceException;
import errors.InvalidStatusException;
import errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.ProductRequest;
import util.NumberGenerator;
import util.models.ItemResponse;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jrodriguez
 */
@Service
@RequiredArgsConstructor
public class SaleOrderServiceImpl implements SaleOrderService {
    private final SaleOrderRepository orderRepository;
    private final SaleOrderItemRepository orderItemRepository;
    private final StatusRepository statusRepository;
    private final ProductClient productClient;
    private final ItemClient itemClient;
    private final NumberGenerator generator;


    /**
     * @param order
     * @param inputOrder ORDEN NUEVA
     *                   CONFIRMADA -> SI
     *                   BORRADOR -> SI
     *                   DEVUELTA -> NO
     */
    @Transactional
    @Override
    public String saveOrder(SaleOrder order, OrderRequest inputOrder) {
        verifyStatus(order);
        setNumberAndStatusAndTotal(order, inputOrder);
        Long idOrder = persistOrder(order);
        saveOrderItem(inputOrder, idOrder);
        return order.getNumber();
    }


    private Long persistOrder(SaleOrder order) {
        return orderRepository.save(order).getId();
    }

    private void verifyStatus(SaleOrder order) {
        Status status = getStatusById(order.getStatus().getId());
        if (status.getName().equals(StatusEnum.DEVUELTA))
            throw new InvalidStatusException(status.getName());
    }

    private void setNumberAndStatusAndTotal(SaleOrder order, OrderRequest inputOrder) {
        String number = generator.generateNumberOrder(order.getIdCategory(), orderRepository.getLast());
        order.setNumber(number);
        order.setStatus(getStatusById(order.getStatus().getId()));
        order.setTotal(calcTotal(inputOrder.getListProducts()));
    }

    private void saveOrderItem(OrderRequest inputOrder, Long idOrder) {
        getItems(inputOrder).forEach(item -> {
            SaleOrderItem orderItem = SaleOrderItem.builder()
                    .idOrder(idOrder)
                    .idItem(item.getId())
                    .build();
            orderItemRepository.save(orderItem);
        });
    }

    private List<ItemResponse> getItems(OrderRequest inputOrder) {
        List<Long> idItems = itemClient.newItem(inputOrder).getBody();
        return idItems.stream().map(ItemResponse::new).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public SaleOrder getByNumber(String number) {
        return orderRepository.getByNumber(number).orElseThrow(() -> new NotFoundException(number));
    }

    @NotNull
    private Status getStatusById(Long idStatus) {
        return statusRepository.findById(idStatus).orElseThrow();
    }

    @Transactional
    @Override
    public void updateOrder(OrderUpdateRequest orderUpdate, String number) {
        SaleOrder order = getByNumber(number);
        Optional.ofNullable(orderUpdate.getCustomer()).ifPresent(order::setCustomer);
        Optional.ofNullable(orderUpdate.getDate()).ifPresent(order::setDate);
        persistOrder(order);
    }

    @Transactional
    @Override
    public void changeStatusOrder(String numberOrder, Long idStatus) {
        Status newStatus = getStatusById(idStatus);
        SaleOrder order = getByNumber(numberOrder);
        validateChangeStatus(newStatus, order);
        order.setStatus(newStatus);
        persistOrder(order);
    }

    /**
     * @param status
     * @param order  CONFIRMADA -> BORRADOR NO
     *               CONFIRMADA -> DEVUELTA SI
     *               BORRADOR   -> CONFIRMADA SI
     *               BORRADOR   -> DEVUELTA NO
     *               DEVUELTA   -> A NINGUNA
     *               DEVUELTA SI Y SOLO SI EL ESTATUS ACTUAL ES CONFIRMADA
     */
    private void validateChangeStatus(Status status, SaleOrder order) {
        StatusEnum statusActual = order.getStatus().getName();
        StatusEnum newStatus = status.getName();

        switch (statusActual) {
            case CONFIRMADA -> {
                if (newStatus.equals(StatusEnum.BORRADOR)) throw new InvalidStatusChanceException(newStatus);
                rollBackStock(order.getId());
                order.setStatus(status);
                persistOrder(order);
            }
            case BORRADOR -> {
                if (newStatus.equals(StatusEnum.DEVUELTA)) throw new InvalidStatusChanceException(newStatus);
            }
            case DEVUELTA -> throw new InvalidStatusChanceException(newStatus);
        }
    }

    @Transactional
    @Override
    public void deleteOrder(String numberOrder) {
        SaleOrder orderExist = getByNumber(numberOrder);
        StatusEnum statusActual = orderExist.getStatus().getName();
        Long idOrder = orderExist.getId();
        if (!statusActual.equals(StatusEnum.BORRADOR)) throw new DeletionInvalidException(idOrder);
        rollBackStock(idOrder);
        orderItemRepository.findByIdOrder(idOrder).forEach(orderItemRepository::delete);
        orderRepository.delete(orderExist);
    }

    @Transactional
    private void rollBackStock(Long idOrder) {
        List<Long> listIdItems = orderItemRepository.findByIdOrder(idOrder).stream()
                .map(SaleOrderItem::getIdItem)
                .collect(Collectors.toList());
        ItemsEditDto request = ItemsEditDto.builder().listItems(listIdItems).build();
        itemClient.editStockProducts(request, true);
    }


    private Double calcTotal(List<ProductRequest> listProducts) {
        return productClient.calcTotal(listProducts);
    }
}
