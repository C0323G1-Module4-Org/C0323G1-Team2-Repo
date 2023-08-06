package com.example.coffee_project.service.user.impl;

import com.example.coffee_project.model.user.EmployeeType;
import com.example.coffee_project.repository.user.IEmployeeTypeRepository;
import com.example.coffee_project.service.user.IEmployeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeTypeService implements IEmployeeTypeService {
    @Autowired
    private IEmployeeTypeRepository employeeTypeRepository;
    @Override
    public List<EmployeeType> findAll() {
        return employeeTypeRepository.findAll();
    }

    @Override
    public EmployeeType findByEmployeeTypeName(String name) {
        return employeeTypeRepository.findEmployeeTypeByEmployeeTypeName(name);
    }
}
