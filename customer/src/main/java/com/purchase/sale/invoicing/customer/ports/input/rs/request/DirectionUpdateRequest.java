package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectionUpdateRequest {

    @JsonProperty("street")
    private String street;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("province")
    private ProvinceRequest province;

    @JsonProperty("location")
    private LocationRequest location;

    @JsonProperty("postal_code")
    private Integer postalCode;
}
