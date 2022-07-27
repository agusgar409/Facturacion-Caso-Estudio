package com.purchase.sale.invoicing.customer.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDetails {

    private Long id;

    private Long dni;

    private String email;

    private String name;

    @JsonProperty("direction")
    private DirectionResponse directionResponse;

    @JsonProperty("type")
    private Set<CustomerTypeResponse> typeResponses;


}
