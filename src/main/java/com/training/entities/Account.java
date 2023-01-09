package com.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Account implements Serializable {

    @Id
    private String accountID;

    @NotNull
    private String address;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private String gender;
    @NotNull
    @Column(unique = true)
    private String identityCard;
    private String image;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String phoneNumber;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;
    @NotNull
    @Column(unique = true)
    private String userName;

    @ToString.Exclude
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountRole> accountRoles = new ArrayList<>();

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Member member;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Employee employee;

    @OneToMany(mappedBy = "account")
    private List<Invoice> invoices = new ArrayList<>();

}
