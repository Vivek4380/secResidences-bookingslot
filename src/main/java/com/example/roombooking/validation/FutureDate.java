package com.example.roombooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default "Booking date must be at least one day ahead";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 