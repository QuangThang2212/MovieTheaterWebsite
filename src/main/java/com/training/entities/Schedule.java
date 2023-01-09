package com.training.entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Schedule implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private LocalTime scheduleTime;

    @OneToMany(mappedBy = "schedule")
    private List<MovieSchedule> movieSchedules = new ArrayList<>();
}
