package com.example.coffee_project.common.customer.custom;

import com.example.coffee_project.service.customer.CustomerService;
import com.example.coffee_project.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;

public class CustomerValidate {

    public static boolean checkValidateBirthday(Date birthday) {
        LocalDate currentDay = LocalDate.now();
        LocalDate localBirthday = birthday.toLocalDate();
        Period period = Period.between(localBirthday, currentDay);
        return period.isNegative();
    }

    public static String validateString(String s) {
        String[] arrayS = s.trim().split("");
        String newString = arrayS[0].toUpperCase();
        for (int i = 1; i < arrayS.length; i++) {
            if (arrayS[i-1].equals(" ") && arrayS[i].equals(" "))
                continue;
            if (arrayS[i - 1].equals(" ")) {
                newString += " " + arrayS[i].toUpperCase();
            } else {
                newString += arrayS[i].toLowerCase();
            }
        }
        return newString;
    }
}
