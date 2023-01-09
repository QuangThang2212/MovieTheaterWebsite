package com.training.service.impl;

import com.training.entities.Seat;
import com.training.entities.Ticket;
import com.training.repository.SeatRepository;
import com.training.repository.TicketRepository;
import com.training.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAllSoldSeatOfRoom(int movieSchedule_id, int movieDate_id) {
        List<Ticket> tickets = ticketRepository.findAllSoldSeatOfRoom(movieSchedule_id, movieDate_id);
        return tickets;
    }
}
