package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.purchase.sale.invoicing.customer.common.annotation.UpperCaseValidate;
import com.purchase.sale.invoicing.customer.ports.input.rs.response.LocationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author jrodriguez
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionRequest {

    @UpperCaseValidate
    @NotEmpty
    @NotBlank
    @JsonProperty("street")
    private String street;

    @NotNull(message = "required field")
    @JsonProperty("height")
    private Integer height;

    @NotNull
    @JsonProperty("province")
    private ProvinceRequest province;

    @NotNull
    @JsonProperty("location")
    private LocationRequest location;

    @NotNull(message = "required field")
    @JsonProperty("postal_code")
    private Integer postalCode;


}
