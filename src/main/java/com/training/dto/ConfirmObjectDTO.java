package com.training.dto;

import com.training.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ConfirmObjectDTO {
    private Account account;
    private Movie movie;
    private Dates dates;
    private Schedule schedule;
    private StringBuffer seatsSelec;
    private List<Seat> seats;
    private Double total;
    private String status;
    private double ConvertScore;

    public ConfirmObjectDTO(Movie movie, Dates dates, Schedule schedule, StringBuffer seatsSelec, List<Seat> seats, Double total, String status, double ConverScore) {
        this.movie = movie;
        this.dates = dates;
        this.schedule = schedule;
        this.seatsSelec = seatsSelec;
        this.seats = seats;
        this.total = total;
        this.status = status;
        this.ConvertScore = ConverScore;
    }
}
