package com.training.service;

import com.training.entities.Dates;

import java.time.LocalDate;
import java.util.List;

public interface DatesService {
    List<Dates> findSixDayFromPresent(String date);
    Dates findByShowDate(LocalDate localDate);
}
