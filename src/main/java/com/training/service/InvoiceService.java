package com.training.service;

import com.training.dto.*;
import com.training.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {
    String save(ConfirmObjectDTO confirmObjectDTO);
    String update(Invoice invoice);
    List<Invoice> findByAccount(Account account);
    List<Invoice> findByAccount(Account account, HistoryScoreDTO historyScoreDTO);
    Page<Invoice> findInvoice(String result, Pageable pageable);
    Invoice findById(String id);
}
