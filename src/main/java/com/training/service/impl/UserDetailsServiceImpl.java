package com.training.service.impl;

import com.training.entities.Account;
import com.training.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByUserName(userName);
        Account account = optionalAccount.orElse(new Account());
        System.out.println(account.getUserName());
        System.out.println(account.getPassword());
        return new User(account.getUserName(), account.getPassword(),
                account.getAccountRoles()
                        .stream()
                        .map(accountRole -> new SimpleGrantedAuthority(accountRole.getRole().getRoleName()))
                        .collect(Collectors.toList()));
    }
}
