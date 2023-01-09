package com.training.repository;

import com.training.entities.Seat;
import com.training.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(nativeQuery = true,
            value = "Select b.* from dbo.invoice a inner join ticket b on a.invoiceid = b.invoice_id where a.movieSchedule_ID = ?1 AND a.movieDate_ID = ?2")
    List<Ticket> findAllSoldSeatOfRoom(int movieSchedule_id, int movieDate_id);
}
