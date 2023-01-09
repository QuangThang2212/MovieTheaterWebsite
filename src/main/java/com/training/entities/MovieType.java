package com.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class MovieType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_type_id")
    private int typeId;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private Type type;

    public MovieType() {
    }

    public MovieType(Movie movie, Type type) {
        this.movie = movie;
        this.type = type;
    }
}
