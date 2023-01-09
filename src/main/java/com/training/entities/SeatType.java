package com.training.entities;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class SeatType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatTypeID;
    private String seatType;
    private double price;

    @OneToMany(mappedBy = "seatType")
    private List<Seat> seats = new ArrayList<>();
}
