package com.example.coffee_project.common.user.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SQLDateValidator implements ConstraintValidator<ValidSQLDate,Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {

//        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//        sdf.setLenient(false); // Không cho phép chuyển đổi ngày không hợp lệ (vd: 2023-02-30)
//        java.util.Date date =
//        try {
//            // Chuyển đổi ngày thành chuỗi và kiểm tra định dạng
//            String dateString = sdf.format(value);
//            Date parsedDate = sdf.parse(dateString);
//            if (!dateString.equals(sdf.format(parsedDate))) {
//                return false; // Ngày không khớp với định dạng
//            }
//        } catch (ParseException e) {
//            return false; // Lỗi xảy ra trong quá trình chuyển đổi
//        }

        return true;
    }
}
