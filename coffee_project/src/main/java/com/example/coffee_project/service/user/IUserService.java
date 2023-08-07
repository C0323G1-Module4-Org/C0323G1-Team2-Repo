package com.example.coffee_project.service.user;

import com.example.coffee_project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> findAll(Pageable pageable, String search);

    void saveUser(User user);

    void removeUser(Integer userId);

    User findByID(Integer id);

    User findByName(String name);
}
