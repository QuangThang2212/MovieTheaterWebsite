package com.training.repository;

import com.training.entities.Dates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DatesReponsitory extends JpaRepository<Dates, Integer> {


    Optional<Dates> findByShowDate(LocalDate localDate);
}
