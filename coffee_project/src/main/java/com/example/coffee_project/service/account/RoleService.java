package com.example.coffee_project.service.account;

import com.example.coffee_project.model.account.Role;
import com.example.coffee_project.repository.account.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository repository;

    @Override
    public Role findByName(String name) {
        return repository.findByRoleName(name);
    }
}
