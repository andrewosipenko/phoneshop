package com.es.core.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = QuantityValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidQuantityAndStock {

    String message() default "Invalid format for quantity(";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
