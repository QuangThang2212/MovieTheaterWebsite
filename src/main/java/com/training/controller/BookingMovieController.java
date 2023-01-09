package com.training.controller;

import com.training.entities.*;
import com.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class BookingMovieController {
    @Autowired
    private DatesService datesService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping({"/", "/showtime"})
    public String showtimePageDefaul(Model model) {
        String showtime = showtimePage( model, String.valueOf(LocalDate.now()));
        return "showtime";
    }
    @GetMapping("/showtime/{date}")
    public String showtimePage(Model model, @PathVariable String date) {
        //show date  on html
        List<Dates> datesResult = datesService.findSixDayFromPresent(String.valueOf(LocalDate.now()));
        if(datesResult.size() >0){
            model.addAttribute("datesResult",datesResult);
        }else{
            return "showtime";
        }

        //find date to search for film
        Dates dates = datesService.findByShowDate(LocalDate.parse(date));
        if(dates == null){
            dates = datesResult.get(0);
        }
        model.addAttribute("dateSlec", dates);

        //show movie which show in that date
        List<Movie> movies = movieService.findAllByDate(dates.getShowDate());
        if(movies.size()==0) {
            return "showtime";
        }

        //show schedule of the movie in that date
        Map<Movie,List<Schedule>> movieListMap = new HashMap<>();

        for(Movie movie: movies){
            movieListMap.put(movie, scheduleService.findAllByMovieId(movie.getMovieId()));
        }

        model.addAttribute("movieListMap", movieListMap);
        model.addAttribute("LocalTime", LocalTime.now());
        model.addAttribute("LocalDate", LocalDate.now());
        return "showtime";
    }
}
