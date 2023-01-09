package com.training.service;

import com.training.dto.ScheduleSeatDTO;
import com.training.entities.Invoice;
import com.training.entities.Schedule_seat;

import java.time.LocalDate;
import java.util.List;

public interface SeatScheduleService {
    List<Schedule_seat> listOfSeatByMovieDateSchedule(String movieID, LocalDate date, int ScheduleID);

}
