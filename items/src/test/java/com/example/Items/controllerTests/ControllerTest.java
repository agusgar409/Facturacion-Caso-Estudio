package com.example.Items.controllerTests;


import com.example.Items.H2Config;
import com.example.Items.api.ApiConstants;
import com.example.Items.model.response.OrderResponseList;
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
import util.TemplateResponse;
import util.AbstractTest;
import util.models.ItemsEditDto;
import util.models.OrderRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
class ControllerTest extends AbstractTest{

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @MockBean
    TemplateResponse templateResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    void createNewItem() throws Exception{
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
    void getOrderWithoutSpecification_return200_andCuantityOf6() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        org.assertj.core.api.Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        org.assertj.core.api.Assertions.assertThat(responseList.getTotalElements()).isEqualTo(6);
        org.assertj.core.api.Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/item?page=1");
        org.assertj.core.api.Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/item?page=0");
        org.assertj.core.api.Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(3)
    void getOrderByIdProduct_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?product=2")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        org.assertj.core.api.Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        org.assertj.core.api.Assertions.assertThat(responseList.getTotalElements()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseList.getTotalPages()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/item/?product=2&page=1");
        org.assertj.core.api.Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/item/?product=2&page=0");
        org.assertj.core.api.Assertions.assertThat(responseList.getContent()).isNotEmpty();

    }

    @Test
    @Order(4)
    void getOrderByCategory_return200() throws Exception {
        String content = mockMvc.perform(get(ApiConstants.ORDER_URI.concat("/?category=2")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        org.assertj.core.api.Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        org.assertj.core.api.Assertions.assertThat(responseList.getTotalElements()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseList.getTotalPages()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/item/?category=2&page=1");
        org.assertj.core.api.Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/item/?category=2&page=0");
        org.assertj.core.api.Assertions.assertThat(responseList.getContent()).isNotEmpty();

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

        org.assertj.core.api.Assertions.assertThat(content).isNotBlank();

        OrderResponseList responseList = mapToObject(content, OrderResponseList.class);

        org.assertj.core.api.Assertions.assertThat(responseList.getTotalElements()).isEqualTo(6);
        org.assertj.core.api.Assertions.assertThat(responseList.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseList.getNextUri()).isEqualTo("http://localhost/item/?size=3&page=1");
        org.assertj.core.api.Assertions.assertThat(responseList.getPreviousUri()).isEqualTo("http://localhost/item/?size=3&page=0");

    }


    @Test
    @Order(6)
    void editStockProducts() throws Exception {
        List<Long> list = List.of(1L,2L,3L);

        ItemsEditDto itemsEditDto = ItemsEditDto.builder()
                .listItems(list)
                .build();

        MockHttpServletResponse response = mockMvc.perform(put(ApiConstants.ORDER_URI.concat("/edit/true"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(itemsEditDto)))
                .andDo(print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());

    }
}
