package com.example.coffee_project.service.user;

import com.example.coffee_project.model.user.EmployeeType;

import java.util.List;

public interface IEmployeeTypeService {
    List<EmployeeType> findAll();

    EmployeeType findByEmployeeTypeName(String s);
}
