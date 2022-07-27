package com.purchase.sale.invoicing.customer.ports.input.rs.api;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.function.Function;

/**
 * @author jrodriguez
 */
public interface ApiConstants {

    String Customer_URI = "v1/customer";
    String Direction_URI = "v1/direction";

    Function<Integer, String> uriByPageAsString = (page) ->
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", page).toUriString();
}
