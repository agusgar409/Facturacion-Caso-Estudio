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
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceRequest {

    private Integer id;

    private String name;
}
