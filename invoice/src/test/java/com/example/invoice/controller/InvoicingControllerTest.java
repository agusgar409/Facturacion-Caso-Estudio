package com.example.invoice.controller;

import com.H2Config;
import com.example.invoice.api.ApiConstants;
import com.example.invoice.model.response.OrderResponseList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import request.ProductRequest;
import util.AbstractTest;
import util.TemplateResponse;
import util.models.OrderRequest;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InvoicingControllerTest extends AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    TemplateResponse templateResponse;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    void createNewInvoice() throws Exception {

        ProductRequest productRequest = ProductRequest.builder()
                .id(1)
                .amount(5)
                .price(20D)
                .build();

        OrderRequest orderRequestInvoice = OrderRequest.builder()
                .category(3)
                .customer(42013929L)
                .description("prueba junit")
                .status(2L)
                .total(120D)
                .date(LocalDate.ofEpochDay(2022-8-3))
                .listProducts(Collections.singletonList(productRequest))
                .build();

        MockHttpServletResponse response = mockMvc.perform(post(ApiConstants.ORDER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(orderRequestInvoice)))
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Order(2)
    void getOrderByNumber_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?number=F-2022-7-5")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        Assertions.assertThat(responseList.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/invoice/?number=F-2022-7-5&page=1");
        Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/invoice/?number=F-2022-7-5&page=0");
        Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(2)
    void getOrderByDniCustomer_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?customer=3")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        Assertions.assertThat(responseList.getTotalElements()).isEqualTo(3);
        Assertions.assertThat(responseList.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/invoice/?customer=3&page=1");
        Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/invoice/?customer=3&page=0");
        Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(3)
    void getOrderByStatus_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?status=2")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        Assertions.assertThat(responseList.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(responseList.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/invoice/?status=2&page=1");
        Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/invoice/?status=2&page=0");
        Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(4)
    void getOrderByCategory_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?idCategory=3")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        Assertions.assertThat(responseList.getTotalElements()).isEqualTo(8);
        Assertions.assertThat(responseList.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/invoice/?idCategory=3&page=1");
        Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/invoice/?idCategory=3&page=0");
        Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(5)
    void getOrderBySize3_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?size=3")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        Assertions.assertThat(responseList.getTotalElements()).isEqualTo(8);
        Assertions.assertThat(responseList.getTotalPages()).isEqualTo(3);
        Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/invoice/?size=3&page=1");
        Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/invoice/?size=3&page=0");
        Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(6)
    void changeStatusCorrectlyTest() throws Exception {

        mockMvc.perform(put(ApiConstants.ORDER_URI.concat("/F-2022-7-7/?status=2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print())
                .andReturn().getResponse();

    }

    @Test
    @Order(7)
    void deleteInvoice() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(put(ApiConstants.ORDER_URI.concat("/F-2022-7-4/?status=1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response).isNotNull();

        mockMvc.perform(delete("/invoice/F-2022-7-4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone())
                .andDo(print());
    }
}