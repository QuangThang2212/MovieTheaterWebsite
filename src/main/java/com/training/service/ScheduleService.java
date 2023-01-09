package com.training.service;

import com.training.entities.Movie;
import com.training.entities.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule findById (int ScheduleID);
    List<Schedule> findAllByMovieId(String movieID);
}
