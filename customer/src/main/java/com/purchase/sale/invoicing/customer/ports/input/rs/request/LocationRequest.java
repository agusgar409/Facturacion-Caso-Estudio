package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jrodriguez
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    private Long id;
    private String name;
}
