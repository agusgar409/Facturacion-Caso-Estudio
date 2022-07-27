package com.purchase.sale.invoicing.customer.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UpperCaseValidation implements ConstraintValidator<UpperCaseValidate,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches("([A-Z]+.)+", value);
    }
}
