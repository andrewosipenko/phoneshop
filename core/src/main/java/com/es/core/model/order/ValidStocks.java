package com.es.core.model.order;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStocksConstraint.class)
public @interface ValidStocks {

    String message() default "{order.notValidStock}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
