package com.api.idsa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearRange {
    String message() default "End year must be greater than start year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
