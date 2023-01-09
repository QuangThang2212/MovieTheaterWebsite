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
@ToString
public class Seat implements Serializable, Comparable<Seat> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    private String seatColumn;
    private String seatRow;



    @ManyToOne
    @JoinColumn(name = "room_id")
    private CinemaRoom cinemaRoom;

    @ManyToOne
    @JoinColumn(name = "seatType_id")
    private SeatType seatType;

    @OneToMany(mappedBy = "seat")
    private List<Schedule_seat> scheduleSeats = new ArrayList<>();


    @Override
    public int compareTo(Seat seat) {
        String a = this.seatRow + this.seatColumn;
        return a.compareTo(seat.seatRow + seat.seatColumn);
    }
}
