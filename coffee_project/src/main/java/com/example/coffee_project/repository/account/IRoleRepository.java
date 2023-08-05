package com.example.coffee_project.repository.account;

import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.model.account.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer>{
    Role findByRoleName(String name);
}
