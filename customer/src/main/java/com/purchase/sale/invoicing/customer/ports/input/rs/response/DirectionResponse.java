package com.purchase.sale.invoicing.customer.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionResponse {

    private String street;

    private Integer height;

    private ProvinceResponse province;

    private LocationResponse location;

    private Integer postalCode;

}
