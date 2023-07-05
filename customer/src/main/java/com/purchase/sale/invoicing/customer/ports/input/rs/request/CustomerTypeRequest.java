package com.purchase.sale.invoicing.customer.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author jrodriguez
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTypeRequest {

    @NotNull(message = "required field")
    private Long id;
}
