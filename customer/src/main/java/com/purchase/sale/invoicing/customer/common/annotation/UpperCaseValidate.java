package com.purchase.sale.invoicing.customer.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = UpperCaseValidation.class)
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UpperCaseValidate {
    String message() default "El campo debe estar en mayusculas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
