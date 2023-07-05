package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Set;

/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateRequest {

    @JsonProperty("dni")
    private Long dni;

    @Email
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastname")
    private String lastname;

    @Valid
    @JsonProperty("direction")
    private DirectionUpdateRequest directionRequest;

    @JsonProperty("type")
    private Set<CustomerTypeRequest> typesRequest;
}
