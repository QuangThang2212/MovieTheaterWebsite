package com.training.service.impl;

import com.training.entities.Dates;
import com.training.entities.MovieDate;
import com.training.repository.MovieDateRepoditory;
import com.training.service.MovieDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDateServiceImpl implements MovieDateService {

    @Autowired
    private MovieDateRepoditory movieDateRepoditory;
    @Override
    public List<MovieDate> findByDates(Dates dates) {
        List<MovieDate> movieDates = movieDateRepoditory.findByDates(dates);
        return movieDates;
    }
}
