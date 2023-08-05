package com.example.coffee_project.dto.customer;

import com.example.coffee_project.common.customer.custom.CustomerValidate;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;

public class CustomerDto implements Validator {
    private Integer customerId;
    private String customerName;
    private Boolean customerGender;
    private Date customerBirthday;
    private String customerPhoneNumber;
    private Integer customerPoint;

    public CustomerDto() {
    }

    public CustomerDto(String customerName, Boolean customerGender, Date customerBirthday, String customerPhoneNumber, Integer customerPoint) {
        this.customerName = customerName;
        this.customerGender = customerGender;
        this.customerBirthday = customerBirthday;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerPoint = customerPoint;
    }

    public CustomerDto(Integer customerId, String customerName, Boolean customerGender, Date customerBirthday, String customerPhoneNumber, Integer customerPoint) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerGender = customerGender;
        this.customerBirthday = customerBirthday;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerPoint = customerPoint;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(Boolean customerGender) {
        this.customerGender = customerGender;
    }

    public Date getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(Date customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Integer getCustomerPoint() {
        return customerPoint;
    }

    public void setCustomerPoint(Integer customerPoint) {
        this.customerPoint = customerPoint;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDto customerDto = (CustomerDto) target;
        // Check name
        if (customerDto.getCustomerName().trim().equals("")) {
            errors.rejectValue("customerName", null, "Không được để trống tên!");
        } else if (customerDto.getCustomerName().length() > 255) {
            errors.rejectValue("customerName", null, "Tên quá dài! Không nhập quá 255 ký tự!");
        } else if (!customerDto.getCustomerName().matches("^[\\p{Lu}][\\p{Ll}]*([\\s][\\p{Lu}][\\p{Ll}]*)*$")) {
            errors.rejectValue("customerName", null, "Tên phải viết hoa chữ cái đầu và không được chứa ký tự đặc biệt!");
        }
        // Check gender
        if (customerDto.getCustomerGender() == null) {
            errors.rejectValue("customerGender", null, "Không được để trống giới tính!");
        }
        // Check birthday
        if (customerDto.getCustomerBirthday() == null) {
            errors.rejectValue("customerBirthday", null, "Không được để trống ngày sinh!");
        } else if (CustomerValidate.checkValidateBirthday(customerDto.getCustomerBirthday())) {
            errors.rejectValue("customerBirthday", null, "Thời gian bạn nhập không hợp lệ!");
        }
        // Check number phone
        if (customerDto.getCustomerPhoneNumber().trim().equals("")) {
            errors.rejectValue("customerPhoneNumber", null, "Không được để trống số điện thoại");
        } else if (!customerDto.getCustomerPhoneNumber().matches("^(84|0[3|5|7|8|9])+([0-9]{8})\\b$")) {
            errors.rejectValue("customerPhoneNumber", null, "Bạn nhập sai định dạng số điện thoại!");
        }
    }
}
