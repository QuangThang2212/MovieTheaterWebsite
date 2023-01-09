package com.training.repository;

import com.training.entities.Invoice;
import com.training.entities.Schedule_seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeatScheduleRepository extends JpaRepository<Schedule_seat, String> {
    @Query( nativeQuery = true,
            value = "SELECT b.* \n" +
                    "FROM dbo.invoice a INNER JOIN dbo.schedule_seat b ON a.invoiceid = b.invoice_id INNER JOIN dbo.dates c ON b.date_id = c.id\n" +
                    "WHERE a.movie_id = ?1 AND c.show_Date =  ?2 AND b.schedule_id = ?3")
    List<Schedule_seat> listOfSeatByMovieDateSchedule(String movieID, LocalDate date, int ScheduleID);
    @Query(nativeQuery = true,
            value = "select *\n" +
                    "from dbo.schedule_seat a\n" +
                    "where a.date_id=?1 and a.schedule_id=?2  and seat_id=?3 ")
    Schedule_seat findByDatesAndScheduleAndSeat(int dateid, int scheduleid, int seatid);
}
