package com.example.coffee_project.service.user.impl;

import com.example.coffee_project.model.user.User;
import com.example.coffee_project.repository.user.IUserRepository;
import com.example.coffee_project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public Page<User> findAll(Pageable pageable, String search) {
        return userRepository.findUserByUserNameContaining(pageable,search);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void removeUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByID(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findUserByAccountAccountName(name);
    }
}
