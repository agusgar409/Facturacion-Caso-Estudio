package com.purchase.sale.invoicing.customer.common.exception.errors;
/**
 * @author jrodriguez
 */
public class EmailAlreadyBoundException extends RuntimeException {

    public EmailAlreadyBoundException(String email) {
        super("email: ".concat(email).concat(" already exists"));
    }
}
