package com.training.repository;

import com.training.entities.Movie;
import com.training.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

//    @Query(value = "SELECT s FROM Schedule s WHERE s.scheduleTime:=schedule", nativeQuery = true)
    Optional<Schedule> findByScheduleTime(LocalTime schedule);

//    @Query(value = "SELECT s.schedule_time FROM schedule s JOIN movie_schedule m ON s.id = m.schedule_id WHERE m.movie_id = :movieId", nativeQuery = true)
//    List<LocalTime> findbyMovieID(@Param("movieId") String movieId);

    @Query(value = "SELECT s.scheduleTime FROM Schedule s JOIN MovieSchedule m ON s.id = m.schedule.id WHERE m.movie.movieId = :movieId")
    List<LocalTime> findbyMovieID(@Param("movieId") String movieId);

    @Query(value = "SELECT m.id FROM Schedule s JOIN MovieSchedule m ON s.id = m.schedule.id WHERE m.movie.movieId = :movieId")
    List<Integer> findmsIDbyMovieID(@Param("movieId") String movieId);

//    @Query(nativeQuery = true, value = "SELECT c.* FROM dbo.movie a INNER JOIN dbo.movie_schedule b ON a.movie_id = b.movie_id INNER JOIN dbo.schedule c ON b.id = c.id WHERE a.movie_id = ?1 ORDER BY c.schedule_time ASC")
//    List<Schedule> findAllByMovieId(String movieID);

    @Query(value = "SELECT m.schedule FROM MovieSchedule m WHERE m.movie.movieId = :movieId")
    List<Schedule> findAllByMovieId(@Param("movieId") String movieId);

}
