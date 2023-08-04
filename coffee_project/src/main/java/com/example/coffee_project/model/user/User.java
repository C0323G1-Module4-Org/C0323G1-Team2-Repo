package com.example.coffee_project.model.user;

import com.example.coffee_project.model.account.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_gender")
    private Boolean userGender;

    @Column(name = "user_birthday", nullable = false)
    private Date userBirthday;

    @Column(name = "user_phone_number", nullable = false, unique = true)
    private String userPhoneNumber;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_id_card", nullable = false, unique = true)
    private String userIdCard;

    @Column(name = "user_salary", nullable = false)
    private Double userSalary;

    @Column(name = "user_address", nullable = false)
    private String userAddress;

    @Column(name = "user_image_path", columnDefinition = "MEDIUMTEXT")
    private String userImagePath;
    @ManyToOne
    @JoinColumn(name = "employee_type_id", referencedColumnName = "employee_type_id")
    private EmployeeType employeeType;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "account_name", referencedColumnName = "account_name", unique = true, nullable = false)
    private Account account;

    public User() {
    }

    public User(Integer userId, String userName, Boolean userGender, Date userBirthday, String userPhoneNumber,
                String userEmail, String userIdCard, Double userSalary, String userAddress, String userImagePath, EmployeeType employeeType, Account account) {
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
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUserGender() {
        return this.userGender;
    }

    public void setUserGender(Boolean userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthday() {
        return this.userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserPhoneNumber() {
        return this.userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIdCard() {
        return this.userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public Double getUserSalary() {
        return this.userSalary;
    }

    public void setUserSalary(Double userSalary) {
        this.userSalary = userSalary;
    }

    public String getUserAddress() {
        return this.userAddress;
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


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }


}
