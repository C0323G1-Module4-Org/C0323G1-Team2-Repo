package com.example.coffee_project.service.user;

import com.example.coffee_project.dto.user.UserDto;
import com.example.coffee_project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public interface IUserService {
    Page<User> findAll(Pageable pageable, String search);

    void saveUser(User user);

    void removeUser(Integer userId);

    User findByID(Integer id);

    User findByName(String name);

    User findByPhoneNumber(String userPhoneNumber);

    User findByEmail(String userEmail);

    User findByIdCard(String userIdCard);

    void checkUniqueAttribute(UserDto userDto, Errors errors);
}
