package com.training.repository;

import com.training.entities.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    @Modifying(clearAutomatically = true)
    @Query("delete from AccountRole ar where ar.id = ?1")
    void deleteById(int AccountRoleID);
}
