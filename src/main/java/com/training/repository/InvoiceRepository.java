package com.training.repository;

import com.training.entities.Account;
import com.training.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findByAccount(Account account);

    @Query(nativeQuery = true, value = "SELECT a.* \n" +
                    "FROM dbo.invoice a INNER JOIN dbo.account b ON a.account_id=b.accountid\n" +
                    "WHERE a.invoiceid = ?1 OR a.account_id = ?1 OR  b.phone_number = ?1 OR b.identity_card = ?1",
            countQuery = "SELECT COUNT(a.invoiceid)\n" +
                    "FROM dbo.invoice a INNER JOIN dbo.account b ON a.account_id=b.accountid\n" +
                    "WHERE a.invoiceid = ?1 OR a.account_id = ?1 OR  b.phone_number = ?1 OR b.identity_card = ?1")
    Page<Invoice> findInvoice(String result, Pageable pageable);
}
