package com.example.coffee_project.repository.user;

import com.example.coffee_project.model.user.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeTypeRepository extends JpaRepository<EmployeeType, Integer> {
    EmployeeType findEmployeeTypeByEmployeeTypeName(String name);
}
