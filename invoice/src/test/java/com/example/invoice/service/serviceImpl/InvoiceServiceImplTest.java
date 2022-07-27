package com.example.invoice.service.serviceImpl;

import com.example.invoice.dto.InvoiceDto;
import com.example.invoice.dto.InvoiceUpdate;
import com.example.invoice.mapper.InvoiceMapperStruct;
import com.example.invoice.model.InvoiceEntity;
import com.example.invoice.model.InvoiceItems;
import com.example.invoice.repository.InvoiceItemRepository;
import com.example.invoice.repository.InvoiceRepository;
import errors.DeletionInvalidException;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import util.NumberGenerator;
import util.TemplateResponse;
import util.models.ItemResponse;
import util.models.OrderRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InvoiceServiceImplTest {


    @Spy
    private InvoiceMapperStruct invoiceMapperStruct = Mappers.getMapper(InvoiceMapperStruct.class);

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceItemRepository invoiceItemRepository;

    @Mock
    private NumberGenerator generator;

    @Mock
    private TemplateResponse templateResponse;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    OrderRequest orderRequest;

    private String stringNumber;

    InvoiceUpdate invoiceUpdate;

    InvoiceEntity invoiceEntity;

    @BeforeEach
    void setUp() {
        stringNumber = "F-2022-7-3";

        orderRequest = OrderRequest.builder()
                .customer(42013929L)
                .date(LocalDate.of(2022, 6, 20))
                .category(2)
                .status(1L)
                .total(1500.0)
                .description("lapiz de escribir")
                .build();

        invoiceUpdate = InvoiceUpdate.builder()
                .categoryUpdate(3)
                .build();

        invoiceEntity = invoiceMapperStruct.inputOrder2InvoiceEntity(orderRequest);
    }

    /*
    @Test
    void createOrderRequestAndSaveInvoiceCorrectly() {

        invoiceEntity.setNumber(stringNumber);
        invoiceEntity.setIdInvoice(3L);

        InvoiceDto invoiceDto = invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntity);

        InvoiceItems invoiceItems = InvoiceItems.builder()
                .idInvoice(1L)
                .idItem(1L)
                .id(1L)
                .build();

        List<ItemResponse> itemResponseList = new ArrayList<>();
        itemResponseList.add(new ItemResponse(1L));

        when(invoiceMapperStruct.inputOrder2InvoiceEntity(orderRequest)).thenReturn(invoiceEntity);
        when(generator.generateNumberOrder(invoiceEntity.getCategory(),invoiceRepository.getLast())).thenReturn(stringNumber);
        when(templateResponse.setItems(orderRequest)).thenReturn(itemResponseList);
        when(invoiceRepository.save(invoiceEntity)).thenReturn(invoiceEntity);
        when(invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntity)).thenReturn(invoiceDto);
        when(templateResponse.calcTotal(orderRequest.getListProducts())).thenReturn(500.0);
        when(invoiceItemRepository.save(invoiceItems)).thenReturn(invoiceItems);

        InvoiceDto response = invoiceService.createOrderRequest(orderRequest);

        Assertions.assertNotNull(response);

        assertThat(response.getStatus()).isEqualTo(1L);
        verify(invoiceItemRepository).save(any(InvoiceItems.class));
    }

    @Test
    void updateInvoiceCorrectly() {

        InvoiceEntity invoiceEntityUpdate = InvoiceEntity.builder()
                .idInvoice(1L)
                .category(3)
                .number("F-2022-7-3")
                .description("lapiz de escribir")
                .customer(42013929L)
                .status(2L)
                .total(300.0)
                .build();

        InvoiceDto invoiceDto = invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntityUpdate);

        invoiceEntity.setNumber(stringNumber);
        invoiceEntity.setStatus(2L);

        when(invoiceRepository.getByNumber(stringNumber)).thenReturn(Optional.of(invoiceEntity));
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoiceEntity));
        when(invoiceMapperStruct.updateInvoice(2L)).thenReturn(invoiceEntityUpdate);
        when(invoiceRepository.save(invoiceEntity)).thenReturn(invoiceEntityUpdate);
        when(invoiceMapperStruct.invoiceEntity2InvoiceDto(invoiceEntityUpdate)).thenReturn(invoiceDto);

        InvoiceDto response = invoiceService.updateInvoice("F-2022-7-3",2L);

        assertEquals(stringNumber,response.getNumber());
        assertThat(response.getCategory()).isEqualTo(3);
        assertThat(response.getStatus()).isEqualTo(2);

    }

    @Test
    void deleteInvoiceCorrectly() {

        invoiceEntity.setStatus(1L);

        when(invoiceRepository.getByNumber(stringNumber)).thenReturn(Optional.ofNullable(invoiceEntity));

        Boolean response = invoiceService.deleteInvoice(stringNumber);

        Assertions.assertEquals(true,response);

    }

    @Test
    void deleteInvoiceWithStatus2Incorrectly() {
        invoiceEntity.setStatus(2L);

        given(invoiceRepository.getByNumber(stringNumber)).willReturn(Optional.ofNullable(invoiceEntity));

        DeletionInvalidException notFoundException = Assertions.assertThrows(DeletionInvalidException.class,
                ()-> invoiceService.deleteInvoice(stringNumber));

        Assertions.assertEquals("resource: 2 cannot be deleted, change status first",
                notFoundException.getMessage());
    }

     */
}