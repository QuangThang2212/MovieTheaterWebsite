package com.training.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Schedule_seat {
    @Id
    private String ScheduleSeatID;
    private String status;

    @ManyToOne
    @JoinColumn(name = "invoice_ID")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "Schedule_ID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "Seat_ID")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "Date_ID")
    private Dates dates;
}
