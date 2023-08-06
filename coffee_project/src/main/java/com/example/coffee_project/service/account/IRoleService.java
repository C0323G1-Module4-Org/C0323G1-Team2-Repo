package com.example.coffee_project.service.account;

import com.example.coffee_project.model.account.Role;

public interface IRoleService{
    Role findByName(String name);
}
