package com.training.service.impl;

import com.training.dto.ScheduleSeatDTO;
import com.training.entities.Invoice;
import com.training.entities.Schedule_seat;
import com.training.repository.SeatScheduleRepository;
import com.training.service.SeatScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SeatScheduleServiceImpl implements SeatScheduleService {

    @Autowired
    private SeatScheduleRepository seatScheduleRepository;

    @Override
    public List<Schedule_seat> listOfSeatByMovieDateSchedule(String movieID, LocalDate date, int ScheduleID) {
        List<Schedule_seat> scheduleSeats = seatScheduleRepository.listOfSeatByMovieDateSchedule(movieID,date,ScheduleID);
        return scheduleSeats;
    }

}
