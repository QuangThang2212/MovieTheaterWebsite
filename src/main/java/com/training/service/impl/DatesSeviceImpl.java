package com.training.service.impl;

import com.training.entities.Dates;
import com.training.repository.DatesRepository;
import com.training.service.DatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DatesSeviceImpl implements DatesService {
    @Autowired
    private DatesRepository datesRepository;

    @Override
    public List<Dates> findSixDayFromPresent(String date) {
        List<Dates> dates = datesRepository.findSixDayFromPresent(date);
        return dates;
    }

    @Override
    public Dates findByShowDate(LocalDate localDate) {
        Optional<Dates> dates = datesRepository.findByShowDate(localDate);
        return dates.orElse(null);
    }
}
