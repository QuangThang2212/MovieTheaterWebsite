package com.training.service;

import com.training.entities.Dates;
import com.training.entities.MovieDate;

import java.util.List;

public interface MovieDateService {
    List<MovieDate> findByDates(Dates dates);
}
