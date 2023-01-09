package com.training.repository;

import com.training.entities.Dates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DatesRepository extends JpaRepository<Dates, Integer>{
    @Query(nativeQuery = true,
            value = "SELECT TOP (6) * FROM dbo.dates a WHERE a.show_Date >= ?1 ORDER BY A.show_Date asc")
    List<Dates> findSixDayFromPresent(String date);

    Optional<Dates> findByShowDate(LocalDate localDate);
}
