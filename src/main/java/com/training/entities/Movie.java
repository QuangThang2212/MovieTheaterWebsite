package com.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Movie implements Serializable {

    @Id
    private String movieId;

    private String actor;
    private String content;
    private String director;
    private int duration;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    private String company;
    @Column(unique = true)
    private String nameEnglish;

    @Column(unique = true, columnDefinition = "nvarchar(255)")
    private String nameVietnam;
    private String image;
    private String version;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL , fetch =FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MovieSchedule> movieSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieType> movieTypes = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieDate> movieDates = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "roomID")
    private CinemaRoom cinemaRoom = new CinemaRoom();
}
