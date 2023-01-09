package com.training.repository;

import com.training.entities.CinemaRoom;
import com.training.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query( nativeQuery = true,
            value = "SELECT c.*\n" +
                    "FROM dbo.movie a INNER JOIN dbo.cinema_room b ON a.roomid = b.roomid INNER JOIN dbo.seat c ON b.roomid = c.room_id INNER JOIN dbo.seat_type d ON c.seat_type_id=d.seat_typeid\n" +
                    "WHERE a.movie_id = ? AND d.seat_type=?")
    List<Seat> findAllByMovieIDAndSeatType(String movieID, String seatType);
    @Query( nativeQuery = true,
            value = "SELECT c.*\n" +
                    "FROM dbo.cinema_room b INNER JOIN dbo.seat c ON b.roomid = c.room_id INNER JOIN dbo.seat_type d ON c.seat_type_id=d.seat_typeid\n" +
                    "WHERE b.roomid = ?1 AND d.seat_type=?2")
    List<Seat> findAllByRoomIDAndSeatType(int roomid, String seatType);
    @Query( nativeQuery = true,
            value = "SELECT c.*\n" +
                    "FROM dbo.invoice a INNER JOIN dbo.schedule_seat b ON a.invoiceid=b.invoice_id INNER JOIN dbo.seat c ON b.seat_id = c.seat_id\n" +
                    "WHERE a.invoiceid = ?1")
    List<Seat> findAllSeatByInvoiceID(String id);
}
