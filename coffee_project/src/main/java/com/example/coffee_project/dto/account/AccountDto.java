package com.example.coffee_project.dto.account;


import javax.validation.constraints.NotBlank;

public class AccountDto {
    @NotBlank(message = "Không được để trống tên tài khoản")
    private String accountName;
    private String accountPassword;

    public AccountDto() {
    }

    public AccountDto(String username, String password) {
        this.accountName = username;
        this.accountPassword = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
