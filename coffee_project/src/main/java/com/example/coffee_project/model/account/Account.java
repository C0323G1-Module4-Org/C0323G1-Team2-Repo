package com.example.coffee_project.model.account;

import com.example.coffee_project.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_password", nullable = false)
    private String accountPassword;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",nullable = false)
    private Role role;
    @OneToOne(mappedBy = "account")
    private User user;

    public Account() {
    }

    public Account(String accountName, String accountPassword, Role role) {
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.role = role;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return this.accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
