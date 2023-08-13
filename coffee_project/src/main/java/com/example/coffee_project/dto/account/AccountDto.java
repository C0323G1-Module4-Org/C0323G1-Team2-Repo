package com.example.coffee_project.dto.account;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountDto {
    @NotBlank(message = "Không được để trống tên tài khoản")
    @Size(min = 5,max = 255,message = "Tên tài khoản phải tên 5 kí tự,dưới 255 kí tự")
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
