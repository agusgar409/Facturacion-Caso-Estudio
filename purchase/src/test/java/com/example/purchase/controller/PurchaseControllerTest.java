package com.example.purchase.controller;

import com.example.purchase.H2Config;
import com.example.purchase.model.response.PurchaseResponse;
import com.example.purchase.model.Item;
import com.example.purchase.model.PurchaseOrder;
import com.example.purchase.model.request.PurchaseOrderRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import request.ProductRequest;
import util.AbstractTest;
import util.TemplateResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PurchaseControllerTest extends AbstractTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;


    @MockBean
    TemplateResponse templateResponse;



    private static PurchaseOrder purchase =  new PurchaseOrder();
    private static PurchaseOrder purchase2 = new PurchaseOrder();
    private static List<PurchaseOrder> purchaseList =  new ArrayList<>();
    private static ProductRequest product = new ProductRequest();

    private static List<Item> list = new ArrayList<>();
    private static Item item = new Item(1L);
    private static List<ProductRequest> listProducts = new ArrayList<>();
    private static PurchaseResponse purchaseResponse = new PurchaseResponse();

    @BeforeAll
    void setUp() {
        product.setAmount(500);
        product.setId(1);
        product.setPrice(20.0);

        list.add(item);

        listProducts.add(product);
        purchase.setCustomer(1L);
        purchase.setTotal(10000.0);
        purchase.setNumber("A");
        purchase.setId(1L);
        purchase.setCategory(1);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(2)
    void getAllPurchases() throws Exception {
        String content = mockMvc.perform(get("http://localhost:8083/purchase/all"))
                .andExpect(status().isFound())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();

    }
    @Test
    @Order(1)
    void deleteByNumber() throws Exception {
        String content = mockMvc.perform(delete("http://localhost:8083/purchase/F-2022-7-1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();

        mockMvc.perform(delete("http://localhost:8083/purchase/F-2023-7-1"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
    @Test
    @Order(3)
    void getPurchaseById() throws Exception{
        String content = mockMvc.perform(get("http://localhost:8083/purchase/F-2022-7-2"))
                .andExpect(status().isFound())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();


        mockMvc.perform(get("http://localhost:8083/purchase/F-2022-7-1"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
    @Test
    @Order(4)
    void changeStatusOrder() throws Exception {
        String content = mockMvc.perform(put("http://localhost:8083/purchase/status/F-2022-7-2/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();
        assertThat(content == "true");

        mockMvc.perform(put("http://localhost:8083/purchase/status/F-2023-7-2"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
    @Test
    @Order(5)
    void newPurchase() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .id(1)
                .amount(5)
                .price(20D)
                .build();

        PurchaseOrderRequest request = PurchaseOrderRequest.builder()
                .customer(42013929L)
                .date(LocalDate.of(2022, 7, 4))
                .status(1L)
                .category(2L)
                .listProducts(Collections.singletonList(productRequest))
                .build();

        mockMvc.perform(post("http://localhost:8083/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);
    }

    @Test
    @Order(6)
    void updateOrder() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .id(1)
                .amount(5)
                .price(20D)
                .build();

        PurchaseOrderRequest request = PurchaseOrderRequest.builder()
                .customer(449L)
                .date(LocalDate.of(2022, 7, 4))
                .status(1L)
                .category(2L)
                .listProducts(Collections.singletonList(productRequest))
                .build();
        mockMvc.perform(patch("http://localhost:8083/purchase/update/F-2022-7-5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}