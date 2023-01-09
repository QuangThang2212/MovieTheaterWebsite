package com.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Member implements Serializable {

    @Id
    private String memberID;

    private double score;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
