package com.example.coffee_project.common.user.custom;

import com.example.coffee_project.common.user.custom.DoubleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE, TYPE_USE })
@Constraint(validatedBy = DoubleValidator.class)
public @interface ValidDouble {
    String message() default "Not double";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
