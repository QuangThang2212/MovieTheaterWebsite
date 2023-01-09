package com.training.service;
import com.training.dto.AccountDTO;
import com.training.entities.Account;
public interface AccountService {
    void save(AccountDTO accountDTO, String roleName);
    Account findByEmailAndPhone(AccountDTO accountDTO);
    boolean findByUserName(AccountDTO accountDTO);
    boolean findByIdentityCard(AccountDTO accountDTO);
    boolean findByEmail(AccountDTO accountDTO);
    boolean findByPhone(AccountDTO accountDTO);
    Account getByUserName(String usename);
    Account findByAccountIDOrIdentityCardAndRoleName(String search, String roleName);
    void update(Account account, AccountDTO accountDTO);
}