package com.example.coffee_project.service.user.impl;

import com.example.coffee_project.dto.user.UserDto;
import com.example.coffee_project.model.user.User;
import com.example.coffee_project.repository.user.IUserRepository;
import com.example.coffee_project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable, String search) {
        return userRepository.findUserByUserNameContaining(pageable, search);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void removeUser(Integer userId) {
        userRepository.removeUserByUserId(userId);
    }

    @Override
    public User findByID(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findUserByAccountAccountName(name);
    }

    @Override
    public User findByPhoneNumber(String userPhoneNumber) {
        return userRepository.findUserByUserPhoneNumber(userPhoneNumber);
    }

    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findUserByUserEmail(userEmail);
    }

    @Override
    public User findByIdCard(String userIdCard) {
        return userRepository.findUserByUserIdCard(userIdCard);
    }

    @Override
    public void checkUniqueAttribute(UserDto userDto, Errors errors) {
        if (findByPhoneNumber(userDto.getUserPhoneNumber()) != null) {
            errors.rejectValue("userPhoneNumber", null, "Số điện thoại này đã được đăng kí!");
        }
        if (findByEmail(userDto.getUserEmail()) != null) {
            errors.rejectValue("userEmail", null, "Email này đã được đăng kí!");
        }
        if (findByIdCard(userDto.getUserIdCard()) != null) {
            errors.rejectValue("userIdCard", null, "Căn cước này đã được đăng kí!");
        }
    }

    @Override
    public Page<User> findNewEmployeeList(Pageable pageable) {
        return userRepository.findNewEmployeeList(pageable);
    }
}