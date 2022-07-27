package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.purchase.sale.invoicing.customer.common.annotation.UpperCaseValidate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestFilter {

    @JsonProperty("dni")
    private Long dni;

    @Email
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("province")
    private String provinceName;

    @UpperCaseValidate
    @JsonProperty("location")
    private String locationName;

    @JsonProperty("type")
    private Long idType;
}
