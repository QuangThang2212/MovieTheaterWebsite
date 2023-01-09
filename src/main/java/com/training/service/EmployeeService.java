package com.training.service;

import com.training.dto.AccountDTO;
import com.training.dto.EmployeeDTO;
import com.training.entities.Employee;
import com.training.util.EmployeeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> listAll();

    Optional<Employee> findById(String employeeID) throws EmployeeNotFoundException;

    void deleteById(String employeeID) throws EmployeeNotFoundException;

    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByemployeeID(String search, Pageable pageable);

}
