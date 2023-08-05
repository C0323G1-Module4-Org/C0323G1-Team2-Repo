package com.example.coffee_project.dto.account;


public class AccountDto {
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
