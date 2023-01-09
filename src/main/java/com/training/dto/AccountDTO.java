package com.training.dto;
import com.training.entities.AccountRole;
import com.training.entities.Employee;
import com.training.entities.Invoice;
import com.training.entities.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
public class AccountDTO {
    private String userName;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String email;
    private String fullName;
    private String gender;
    private String identityCard;
    private String image;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;
    private List<AccountRole> accountRoles = new ArrayList<>();
    private Member member;
    private Employee employee;
    private List<Invoice> invoices = new ArrayList<>();
}