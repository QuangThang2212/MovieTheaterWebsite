package com.training.service.impl;
import com.training.dto.AccountDTO;
import com.training.entities.*;
import com.training.repository.AccountRepository;
import com.training.repository.EmployeeRepository;
import com.training.repository.MemberRepository;
import com.training.service.AccountService;
import com.training.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void save(AccountDTO accountDTO, String roleName) {
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        String email = account.getEmail();
        String phone = account.getPhoneNumber();
        account.setAccountID(email.substring(0,5).concat(phone.substring(phone.length()-5)));
        account.setRegisterDate(LocalDate.now());
        if(account.getGender().equals("Nam")){
            account.setImage("https://i.pinimg.com/564x/a3/43/d2/a343d2b2b470e36c1a6ec12352448ee9.jpg");
        }else{
            account.setImage("https://i.pinimg.com/564x/e4/25/83/e425832883fd4062d126bc974b51d3f0.jpg");
        }
        if(roleName.equals("ROLE_USER")){
            Role role = roleService.findByRoleName("ROLE_USER");
            if (role.getId() == 0) {
                role.setRoleName("ROLE_USER");
            }

            account.setAccountRoles(Arrays.asList(new AccountRole(account, role)));
            accountRepository.save(account);
            Member member = new Member();
            String defaultStartID = "mem";
            member.setMemberID(defaultStartID.concat(account.getAccountID()));
            member.setScore(0);
            member.setAccount(account);
            memberRepository.save(member);

        } else if(roleName.equals("ROLE_EMPLOYEE")){
            Role role = roleService.findByRoleName("ROLE_EMPLOYEE");
            Role roleUser = roleService.findByRoleName("ROLE_USER");
            if (role.getId() == 0) {
                role.setRoleName("ROLE_EMPLOYEE");
            }

//            account.setAccountRoles(Arrays.asList(new AccountRole(account, role)));

            AccountRole accountRole = new AccountRole(account,roleUser);
            account.getAccountRoles().add(accountRole);
            account.getAccountRoles().add(new AccountRole(account, role));
            Employee employee = new Employee();
            String defaultStartID = "emp";
            employee.setEmployeeID(defaultStartID.concat(account.getAccountID()));
            employee.setAccount(account);
            account.setEmployee(employee);

            Member member = new Member();
            String defaultStartID1 = "mem";
            member.setMemberID(defaultStartID.concat(account.getAccountID()));
            member.setScore(0);
            account.setMember(member);
            member.setAccount(account);
            accountRepository.save(account);

//            Employee employee = new Employee();
//            String defaultStartID = "emp";
//            employee.setEmployeeID(defaultStartID.concat(account.getAccountID()));
//            employee.setAccount(account);
//            employeeRepository.save(employee);

//            Member member = new Member();
//            String defaultStartID1 = "mem";
//            member.setMemberID(defaultStartID.concat(account.getAccountID()));
//            member.setScore(0);
//            member.setAccount(account);
//            memberRepository.save(member);

        }
    }

    @Override
    public Account findByEmailAndPhone(AccountDTO accountDTO) {

        return accountRepository.findByEmailAndPhoneNumber(accountDTO.getEmail(), accountDTO.getPhoneNumber());
    }

    @Override
    public boolean findByUserName(AccountDTO accountDTO) {
        if(accountRepository.findByUserNameCheckExist(accountDTO.getUserName()) != null){
            String pass = passwordEncoder.encode("adminadmin");
            System.out.println(pass);
            return true;
        }
        return false;
    }

    @Override
    public boolean findByIdentityCard(AccountDTO accountDTO) {
        if(accountRepository.findByIdentityCardCheckExist(accountDTO.getIdentityCard()) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean findByEmail(AccountDTO accountDTO) {
        if(accountRepository.findByEmailCheckExist(accountDTO.getEmail()) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean findByPhone(AccountDTO accountDTO) {
        if(accountRepository.findByPhoneNumberCheckExist(accountDTO.getPhoneNumber()) != null){
            return true;
        }
        return false;
    }
    public  Account getByUserName(String usename) {
        Optional<Account> optionalAccount = accountRepository.findByUserName(usename);
        return optionalAccount.orElse(null);
    }

    @Override
    public Account findByAccountIDOrIdentityCardAndRoleName(String search,String roleName) {
        Optional<Account> optionalAccount = accountRepository.findByAccountIDOrIdentityCardAndRoleName(search, search, roleName);
        return optionalAccount.orElse(null);
    }
    @Override
    public void update(Account account, AccountDTO accountDTO) {
        account.setImage(accountDTO.getImage());
        account.setGender(accountDTO.getGender());
        account.setAddress(accountDTO.getAddress());
        account.setDateOfBirth(accountDTO.getDateOfBirth());
        account.setFullName(accountDTO.getFullName());
        accountRepository.save(account);
    }

}