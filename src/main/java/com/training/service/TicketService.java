package com.training.service;

import com.training.entities.Seat;
import com.training.entities.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> findAllSoldSeatOfRoom(int movieSchedule_id, int movieDate_id);
}
