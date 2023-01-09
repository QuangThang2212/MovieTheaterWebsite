package com.training.repository;

import com.training.entities.Employee;
import com.training.entities.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Long countByEmployeeID(String employeeID);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Employee e WHERE e.employeeID =?1")
    void deleteByEmployeeID(String employeeID);

    Page<Employee> findByemployeeID(String search, Pageable pageable);

}
