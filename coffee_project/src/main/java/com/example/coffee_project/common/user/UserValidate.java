package com.example.coffee_project.common.user;

import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.sql.Date;
import java.time.Period;

public class UserValidate {
    private static final String REGEX_USER_NAME = "^[\\p{Lu}][\\p{Ll}]*([\\s][\\p{Lu}][\\p{Ll}]*)*$\"";
    private static final String REGEX_NUMBER_PHONE = "((09|03|07|08|05)+([0-9]{8})\\b)";
    private static final String REGEX_EMAIL = "^(.+)@(\\S+)$";

    public static boolean checkRegexUserName(String userName) {
        return userName.matches(REGEX_USER_NAME);
    }

    public static void checkValidateUserName(String userName, Errors errors) {
        if (userName == null) {
            return;
        }
        if (userName.trim().equals("")) {
            return;
        }
        if (userName.length() > 0 && (userName.length() <= 5 || userName.length() > 255)) {
            errors.rejectValue("userName", null,
                    "Tên nhân viên không được ít hơn 5 kí tự và vượt quá 255 kí tự!");
//        } else if (!checkRegexUserName(userName)) {
//            errors.rejectValue("userName", null,
//                    "Tên khách hàng phải ghi hoa kí tự đầu tiên của mỗi chữ cái");
        }
    }

    public static void checkValidateUserBirthday(Date userBirthday, Errors errors) {
        if (userBirthday == null) {
            return;
        }
        LocalDate current = LocalDate.now();
        LocalDate birthday = userBirthday.toLocalDate();
        Period period = Period.between(birthday, current);
        if (period.isNegative()) {
            errors.rejectValue("userBirthday", null, "Thời gian không hợp lệ!");
        } else if (period.getYears() < 18) {
            errors.rejectValue("userBirthday", null, "Tuổi nhân viên phải lớn hơn 18!");
        } else if (period.getYears() > 50) {
            errors.rejectValue("userBirthday", null, "Tuổi nhân viên phải nhỏ hơn 50!");
        }


    }

    public static void checkValidateUserPhoneNumber(String userPhoneNumber, Errors errors) {
        if (userPhoneNumber == null) {
            return;
        }
        if (userPhoneNumber.trim().equals("")) {
            return;
        }
        try {
            Integer.parseInt(userPhoneNumber);
            if (userPhoneNumber.length() != 10) {
                errors.rejectValue("userPhoneNumber", null, "Số điện thoại phải là 10 chữ số!");
            } else if (!userPhoneNumber.matches(REGEX_NUMBER_PHONE)) {
                errors.rejectValue("userPhoneNumber", null, "Số điện thoại không đúng định dạng!");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.rejectValue("userPhoneNumber", null, "Số điện thoại không được chứa kí tự khác số!");
        }

    }

    public static void checkValidateUserEmail(String userEmail, Errors errors) {
        if (userEmail == null) {
            return;
        }
        if (userEmail.trim().equals("")) {
            return;
        }
        if (userEmail.length() > 255) {
            errors.rejectValue("userEmail", null, "Email bị giới hạn 255 kí tự!");
        } else if (!userEmail.matches(REGEX_EMAIL)) {
            errors.rejectValue("userEmail", null, "Email không đúng định dạng!");
        }
    }

    public static void checkValidateUserIdCard(String userIdCard, Errors errors) {
        if (userIdCard == null) {
            return;
        }
        if (userIdCard.trim().equals("")) {
            return;
        }
        try {
            Long.parseLong(userIdCard);
        } catch (NumberFormatException numberFormatException) {
            errors.rejectValue("userIdCard", null, "Mã căn cước không được chứa kí tự khác số!");
        }
        if (userIdCard.length() != 12) {
            errors.rejectValue("userIdCard", null, "Mã căn cước phải đúng 12 số!");
        }
    }

    public static void checkValidateUserUserSalary(Double userSalary, Errors errors) {
        if(userSalary == null){
            return;
        }
        if (userSalary.isNaN() || userSalary.isInfinite()) {
            errors.rejectValue("userSalary", null, "Nhập lương không đúng định dạng!");
        }
    }
}
