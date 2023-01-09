package com.training.repository;

import com.training.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type,Integer> {

    Optional<Type> findByTypeName(String string);


    @Query(value = "SELECT t.typeName FROM Type t JOIN MovieType m ON t.id = m.type.id WHERE m.movie.movieId = :movieId")
    List<String> findTypeNameMovieID(@Param("movieId") String movieId);
}
