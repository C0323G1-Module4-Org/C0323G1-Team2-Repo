package com.example.coffee_project.common.customer.custom;

import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;

public class CustomerValidate {
    public static boolean checkValidateBirthday(Date birthday) {
        LocalDate currentDay= LocalDate.now();
        LocalDate localBirthday = birthday.toLocalDate();
        Period period = Period.between(localBirthday, currentDay);
        return period.isNegative();
    }
}
