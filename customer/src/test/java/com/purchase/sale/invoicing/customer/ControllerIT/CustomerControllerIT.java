package com.purchase.sale.invoicing.customer.ControllerIT;

import com.purchase.sale.invoicing.customer.H2Config;
import com.purchase.sale.invoicing.customer.ports.input.rs.api.ApiConstants;
import com.purchase.sale.invoicing.customer.ports.input.rs.request.*;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseDetails;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.CustomerResponseList;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import util.AbstractTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author jrodriguez
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerIT extends AbstractTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    MockMvc mockMvc;

    private static final String URI = "http://localhost/".concat(ApiConstants.Customer_URI);

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Order(1)
    void createCustomer_shouldReturn_201() throws Exception {

        CustomerTypeRequest typeRequest = new CustomerTypeRequest(1L);
        Set<CustomerTypeRequest> set = new HashSet<>();
        set.add(typeRequest);
        ProvinceRequest provinceRequest = ProvinceRequest.builder().id(6).name("Buenos Aires").build();
        LocationRequest locationRequest = LocationRequest.builder().id(6021010000L).name("ALBERTI").build();
        DirectionRequest direction = DirectionRequest.builder()
                .height(500)
                .location(locationRequest)
                .province(provinceRequest)
                .postalCode(20)
                .street("jose")
                .build();


        CustomerRequest request = CustomerRequest.builder()
                .dni(5589488L)
                .email("jose@gmail.com")
                .name("jose")
                .lastname("Martinez")
                .directionRequest(direction)
                .typesRequest(set)
                .build();

        final String actualLocation = mockMvc.perform(post(URI.concat("/save"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo(URI.concat("/save/1"));

    }

    /**
     * Email ya existente en la db
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    void createCustomer_shouldReturn_400_badEmail() throws Exception {
        CustomerTypeRequest typeRequest = new CustomerTypeRequest(1L);
        Set<CustomerTypeRequest> set = new HashSet<>();
        set.add(typeRequest);
        ProvinceRequest provinceRequest = ProvinceRequest.builder().id(6).name("Buenos Aires").build();
        LocationRequest locationRequest = LocationRequest.builder().id(6021010000l).name("ALBERTI").build();
        DirectionRequest direction = DirectionRequest.builder()
                .height(500)
                .location(locationRequest)
                .province(provinceRequest)
                .postalCode(20)
                .street("jose")
                .build();


        CustomerRequest request = CustomerRequest.builder()
                .dni(5589488L)
                .email("jose@gmail.com")
                .name("jose")
                .lastname("Martinez")
                .directionRequest(direction)
                .typesRequest(set)
                .build();

        mockMvc.perform(post(URI.concat("/save"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    @Order(3)
    void getCustomerDetails_shouldReturn_200() throws Exception {

        String content = mockMvc.perform(get(URI.concat("/1")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        CustomerResponseDetails response = mapToObject(content, CustomerResponseDetails.class);

        assertThat(response.getDni()).isEqualTo(5589488);
        assertThat(response.getEmail()).isEqualTo("jose@gmail.com");
        assertThat(response.getName()).isEqualTo("jose");
        assertThat(response.getDirectionResponse().getStreet()).isEqualTo("Yrigoyen");
        assertThat(response.getDirectionResponse().getHeight()).isEqualTo(1800);
        assertThat(response.getDirectionResponse().getProvince()).isEqualTo("Buenos Aires");
        assertThat(response.getDirectionResponse().getPostalCode()).isEqualTo(5599);

    }

    @Test
    @Order(4)
    void getAllCustomer_shouldReturn_200() throws Exception {
        String content = mockMvc.perform(get(URI.concat("/all")))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotNull();

        CustomerResponseList response = mapToObject(content, CustomerResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/customer/all?page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/customer/all?page=0");
        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    @Order(5)
    void updateCustomerProperties_shouldReturn_200() throws Exception {

        CustomerUpdateRequest request = new CustomerUpdateRequest().builder()
                .name("name update")
                .build();

        mockMvc.perform(patch(URI.concat("/update/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(request)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @Order(6)
    void updateCustomerDirection_shouldReturn_200() throws Exception {

        DirectionUpdateRequest dir = new DirectionUpdateRequest().builder()
                .street("chacabuco")
                .build();


        CustomerUpdateRequest request = new CustomerUpdateRequest().builder()
                .directionRequest(dir)
                .build();

        mockMvc.perform(patch(URI.concat("/update/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(request)))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @Order(7)
    void deleteCustomer_shouldReturn_204() throws Exception {

        mockMvc.perform(delete(URI.concat("/1")))
                .andExpect(status().isNoContent());


    }

}
