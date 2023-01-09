package com.training.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MovieDTO {

    private String movieId;

    @NotBlank
    private String nameEnglish;

    @NotBlank
    private String nameVietnam;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @NotBlank
    private String actor;

    @NotBlank
    private String company;

    @NotBlank
    private String director;

    private int duration;

    @NotBlank
    private String version;

    @NotEmpty(message = "Type cannot be empty.")
    private List<@Valid String> type;

    private int room;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotEmpty(message = "Schedule cannot be empty.")
    private List<@Valid LocalTime> times;

    @NotBlank(message = "Content is empty")
    private String content;

    @NotBlank(message = "Image is empty")
    private String image;


    public MovieDTO() {
    }
}
