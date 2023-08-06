package com.example.coffee_project.common.user.custom;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleValidator implements ConstraintValidator<ValidDouble, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null|| value.isNaN() || value.isInfinite()) {
            return false;
        }

        return true;
    }
}
