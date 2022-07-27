package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.purchase.sale.invoicing.customer.common.annotation.UpperCaseValidate;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CustomerRequest {

    @NotNull(message = "required field")
    @JsonProperty("dni")
    private Long dni;

    @NotEmpty
    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @UpperCaseValidate
    @NotEmpty
    @NotBlank
    @JsonProperty("name")
    private String name;

    @UpperCaseValidate
    @NotEmpty
    @NotBlank
    @JsonProperty("lastname")
    private String lastname;

    @Valid
    @JsonProperty("direction")
    private DirectionRequest directionRequest;

    @JsonProperty("type")
    private Set<CustomerTypeRequest> typesRequest;


}
