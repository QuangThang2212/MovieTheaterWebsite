package com.training.service.impl;

import com.training.entities.Movie;
import com.training.entities.Schedule;
import com.training.repository.ScheduleRepository;
import com.training.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule findById(int ScheduleID) {
        Optional<Schedule> schedule = scheduleRepository.findById(ScheduleID);
        return schedule.orElse(null);
    }

    @Override
    public List<Schedule> findAllByMovieId(String movieID) {
        return scheduleRepository.findAllByMovieId(movieID);
    }
}
