package com.example.coffee_project.repository.user;

import com.example.coffee_project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User,Integer> {
    Page<User> findUserByUserNameContaining(Pageable pageable, String search);
    User findUserByAccountAccountName(String name);
}
