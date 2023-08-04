package com.example.coffee_project.controller.user;

import com.example.coffee_project.model.user.User;
import com.example.coffee_project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class RestUserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<User> showUserInfo(@PathVariable Integer id) {

        User user = userService.findByID(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


