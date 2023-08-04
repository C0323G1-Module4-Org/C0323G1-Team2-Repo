package com.example.coffee_project.common.user.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;

public class SQLDateValidator implements ConstraintValidator<ValidSQLDate,Date> {
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return true;
    }
}
