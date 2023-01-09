package com.training.repository;

import com.training.entities.CinemaRoom;
import com.training.entities.Movie;
import com.training.entities.MovieSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,String> {

    Movie findByMovieId(String movieId);

    @Modifying
    @Query(value = "DELETE FROM movie_schedule WHERE movie_id = :movieId", nativeQuery = true)
    void deletebyMovieID(@Param("movieId") String movieId);

    @Modifying
    @Query(value = "DELETE FROM movie_date WHERE movie_id = :movieId", nativeQuery = true)
    void deleteDatebyMovieID(@Param("movieId") String movieId);

    @Modifying
    @Query(value = "DELETE FROM movie_type WHERE movie_id = :movieId", nativeQuery = true)
    void deleteTypebyMovieID(@Param("movieId") String movieId);

    @Query(value = "SELECT m.movie FROM MovieDate m JOIN Dates d ON m.dates.id = d.id WHERE d.showDate = :localDate and m.movie.cinemaRoom.roomID = :room")
    List<Movie> findListMovieByDate(@Param("localDate") LocalDate localDate, @Param("room") Integer room);

    @Query(value = "SELECT s.scheduleTime FROM Schedule s JOIN MovieSchedule m ON s.id=m.schedule.id WHERE m.movie in :movies and m.movie.cinemaRoom.roomID = :room")
    List<LocalTime> finTimeByListMovie(@Param("movies") List<Movie> movies, @Param("room") Integer room);

    @Query(value = "SELECT s.scheduleTime FROM Schedule s JOIN MovieSchedule m ON s.id=m.schedule.id WHERE m.movie.movieId = :movieId and m.movie.cinemaRoom.roomID = :room")
    List<LocalTime> finTimeByMovieID(@Param("movieId") String movieId, @Param("room") Integer room);

    @Query(nativeQuery = true, value = "SELECT c.* FROM dbo.dates a INNER JOIN dbo.movie_date b ON a.id=b.date_id INNER JOIN dbo.movie c ON b.movie_id = c.movie_id WHERE a.show_date = ?1")
    List<Movie> findAllByDate(LocalDate date);

    Integer countByMovieId(String movieID);

    Page<Movie> findByNameEnglishContaining(String search, Pageable pageable);
}
