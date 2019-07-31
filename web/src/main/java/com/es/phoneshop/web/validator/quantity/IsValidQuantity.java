package com.es.phoneshop.web.validator.quantity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = QuantityConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidQuantity {
    String message() default "Invalid quantity";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
