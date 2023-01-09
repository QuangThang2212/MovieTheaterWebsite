package com.training.service;

import com.training.dto.MovieDTO;
import com.training.entities.Movie;
import com.training.entities.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface MovieService {

    Set<LocalTime> save(MovieDTO movieDTO);

    List<Movie> findAll();

    Movie getMovie(String movieId);

    Movie findById(String string);

    List<LocalTime> findTimeByMovieId(String movieId);

    List<String> findTypeByMovieID(String moviId);

    MovieDTO getMovieDTO(Movie movie);

    List<Movie> findAllByDate(LocalDate date);

    void delete(String movieID);

    Integer countByMovieID(String MovieId);

    Page<Movie> findAllPage(Pageable pageable);

    Page<Movie> findByTitle(String search, Pageable pageable);

}
