package com.es.core.model.order.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderInformationConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidPhone {
    String message() default "Phone must starts with +";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
