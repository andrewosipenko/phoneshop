package com.es.phoneshop.web.validate.sort;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Constraint(validatedBy = OrderByConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderByValid {
    Class<? extends Enum<?>> columnEnum();

    Class<? extends Enum<?>> orderEnum();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Incorrect order by";
}
