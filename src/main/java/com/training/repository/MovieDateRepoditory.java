package com.training.repository;

import com.training.entities.Dates;
import com.training.entities.MovieDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDateRepoditory extends JpaRepository<MovieDate, Integer> {
    List<MovieDate> findByDates(Dates dates);
    @Query(nativeQuery = true, value = "SELECT * FROM movieSchedule WHERE ")
    MovieDate findByDatesAndMovie(int dates, int movie);
}
