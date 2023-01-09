package com.training.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@ToString
public class Employee implements Serializable {

    @Id
    private String employeeID;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;


}
