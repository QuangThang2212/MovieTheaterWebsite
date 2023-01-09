package com.training.service.impl;

import com.training.dto.AccountDTO;
import com.training.dto.EmployeeDTO;
import com.training.entities.Account;
import com.training.entities.AccountRole;
import com.training.entities.Employee;
import com.training.repository.AccountRepository;
import com.training.repository.AccountRoleRepository;
import com.training.repository.EmployeeRepository;
import com.training.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public List<Employee> listAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(String employeeID) {
        return employeeRepository.findById(employeeID);
    }


    @Override
    public void deleteById(String employeeID) {
        Optional<Employee> employee = employeeRepository.findById(employeeID);
        System.out.println("employee: " + employee);
        Account account = employee.get().getAccount();
        System.out.println("account: " + account);
        List<AccountRole> listOfAccountRole = account.getAccountRoles();
        try {
            for(AccountRole accountRole : listOfAccountRole){
                System.out.println(accountRole.getId());
                accountRoleRepository.deleteById(accountRole.getId());
            }
            accountRepository.deleteById(account.getAccountID());
            employeeRepository.deleteByEmployeeID(employeeID);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> findByemployeeID(String search, Pageable pageable) {
        return employeeRepository.findByemployeeID(search, pageable);
    }
}
