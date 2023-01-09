package com.training.service;

import com.training.entities.CinemaRoom;
import com.training.entities.Seat;

import java.util.List;

public interface SeatService {
    Seat findById(int ID);
    List<Seat> findAllByMovieIDAndSeatType(String movieID, String seatType);
    List<Seat> findAllByRoomIDAndSeatType(int roomid, String seatType);
    List<Seat> findAllSeatByInvoiceID(String id);
    String updateListOfSeat(List<Integer> seats);
}
