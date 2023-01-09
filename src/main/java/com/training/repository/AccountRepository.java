
package com.training.repository;
import com.training.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    Optional<Account> findByUserName(String userName);
    @Query("SELECT a FROM Account a WHERE a.email = ?1 AND a.phoneNumber = ?2")
    Account findByEmailAndPhoneNumber(String email, String phoneNumber);
    @Query("SELECT a FROM Account a WHERE a.userName = ?1")
    Account findByUserNameCheckExist(String userName);
    @Query("SELECT a FROM Account a WHERE a.identityCard = ?1")
    Account findByIdentityCardCheckExist(String identityCard);
    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Account findByEmailCheckExist(String email);
    @Query("SELECT a FROM Account a WHERE a.phoneNumber = ?1")
    Account findByPhoneNumberCheckExist(String phoneNumber);
    @Query( nativeQuery = true,
            value = "SELECT *\n" +
                    "FROM dbo.account a INNER JOIN dbo.account_role b ON a.accountid = b.account_id INNER JOIN dbo.role c ON b.role_id = c.id\n" +
                    "WHERE (a.accountid = ?1 OR a.identity_card= ?2) AND c.role_name=?3")
    Optional<Account> findByAccountIDOrIdentityCardAndRoleName(String searchID, String searchIden, String roleName);
}



