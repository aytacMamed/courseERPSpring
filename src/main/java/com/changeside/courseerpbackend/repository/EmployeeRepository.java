package com.changeside.courseerpbackend.repository;

import com.changeside.courseerpbackend.models.mybatis.employee.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeRepository {
    void insert(Employee employee);
}
