package com.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Dates implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private LocalDate showDate;
    @Column(nullable = false)
    private String dateName;

    @OneToMany(mappedBy = "dates")
    private List<MovieDate> movieDates = new ArrayList<>();

    @OneToMany(mappedBy = "dates")
    private List<Schedule_seat> scheduleSeats = new ArrayList<>();
}
