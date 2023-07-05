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
public class CustomerResponse {

    private Long id;

    private Long dni;

    private String email;

    private String name;

    private String lastname;

}
