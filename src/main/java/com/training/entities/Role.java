package com.training.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String roleName;

    @ToString.Exclude
    @OneToMany(mappedBy = "role")
    private List<AccountRole>  accountRoles= new ArrayList<>();
}
