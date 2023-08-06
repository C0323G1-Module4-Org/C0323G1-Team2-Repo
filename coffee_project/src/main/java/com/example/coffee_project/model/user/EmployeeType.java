package com.example.coffee_project.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee_type")
public class EmployeeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_type_id")
    private Integer employeeTypeId;

    @Column(name = "employee_type_name", nullable = false,unique = true)
    private String employeeTypeName;
    @JsonBackReference
    @OneToMany(mappedBy = "employeeType")
    private Set<User> userSet;
    public EmployeeType() {
    }
    public EmployeeType(Integer employeeTypeId, String employeeTypeName, Set<User> userSet) {
        this.employeeTypeId = employeeTypeId;
        this.employeeTypeName = employeeTypeName;
        this.userSet = userSet;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Integer getEmployeeTypeId() {
        return this.employeeTypeId;
    }

    public void setEmployeeTypeId(Integer employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public String getEmployeeTypeName() {
        return this.employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }
}
