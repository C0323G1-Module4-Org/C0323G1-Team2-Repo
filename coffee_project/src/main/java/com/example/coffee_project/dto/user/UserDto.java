package com.example.coffee_project.dto.user;

import com.example.coffee_project.common.user.UserValidate;
import com.example.coffee_project.common.user.custom.ValidDouble;
import com.example.coffee_project.common.user.custom.ValidSQLDate;
import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.model.user.EmployeeType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class UserDto implements Validator {
    private Integer userId;
    @NotBlank(message = "Không được để trống tên!")
    private String userName;
    @NotNull(message = "Không được để trống giới tính!")
    private Boolean userGender;
    @ValidSQLDate(message = "Không được để trống ngày sinh!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;
    @NotBlank(message = "Không được để trống số điện thoại!")
    private String userPhoneNumber;
    @NotBlank(message = "Không được để trống mail!")
    private String userEmail;
    @NotBlank(message = "Không được để trống giấy tờ tuỳ thân!")
    private String userIdCard;
    private Double userSalary;
    @NotBlank(message = "Không được để trống địa chỉ!")
    private String userAddress;
    private String userImagePath;
    private EmployeeType employeeType;
    private Account account;

    public UserDto() {
    }

    public UserDto(Integer userId, String userName, Boolean userGender, Date userBirthday, String userPhoneNumber, String userEmail, String userIdCard, Double userSalary, String userAddress, String userImagePath, EmployeeType employeeType, Account account) {
        this.userId = userId;
        this.userName = userName;
        this.userGender = userGender;
        this.userBirthday = userBirthday;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userIdCard = userIdCard;
        this.userSalary = userSalary;
        this.userAddress = userAddress;
        this.userImagePath = userImagePath;
        this.employeeType = employeeType;
        this.account = account;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUserGender() {
        return userGender;
    }

    public void setUserGender(Boolean userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public Double getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(Double userSalary) {
        this.userSalary = userSalary;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        UserValidate.checkValidateUserName(userDto.getUserName(),errors);
        UserValidate.checkValidateUserBirthday(userDto.getUserBirthday(),errors);
        UserValidate.checkValidateUserPhoneNumber(userDto.getUserPhoneNumber(),errors);
        UserValidate.checkValidateUserEmail(userDto.getUserEmail(),errors);
        UserValidate.checkValidateUserIdCard(userDto.getUserIdCard(),errors);
        UserValidate.checkValidateUserUserSalary(userDto.getUserSalary(),errors);
    }
}
