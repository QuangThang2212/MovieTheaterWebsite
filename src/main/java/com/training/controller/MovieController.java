package com.training.controller;

import com.training.dto.MovieDTO;
import com.training.entities.CinemaRoom;
import com.training.entities.Movie;
import com.training.entities.Promotion;
import com.training.service.CinemaRoomService;
import com.training.service.ImageService;
import com.training.service.MovieService;
import com.training.util.PromotionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaRoomService cinemaRoomService;

    @Autowired
    ImageService imageService;


    @GetMapping("/movie/add-movie")
    public String addMovieget(Model model) {
        List<CinemaRoom> cinemaRooms = cinemaRoomService.listAll();
        List<String> time = new ArrayList<>();
        MovieDTO movieDTO = new MovieDTO();
        List<String> type = new ArrayList<>();
        movieDTO.setType(type);

        model.addAttribute("cinemaRooms", cinemaRooms);
        model.addAttribute("movieDTO", movieDTO);
        model.addAttribute("time", time);
        model.addAttribute("pageTitle", "Add New Movie");
        return "addMovie";
    }

    @PostMapping("/movie/add-movie")
    public String addMoviepost(@Valid @ModelAttribute MovieDTO movieDTO, Model model, BindingResult bindingResult, RedirectAttributes re) throws Exception {

        System.out.println(movieDTO);
//
//        if(movieDTO.getType().isEmpty()){
//            ObjectError objectError = new ObjectError("MovieDTO", "Type must not empty");
//            bindingResult.addError(objectError);
//        }
//
//        if(movieDTO.getRoom() == 0){
//            ObjectError objectError = new ObjectError("MovieDTO", "Plese select room");
//            bindingResult.addError(objectError);
//        }
        Set<LocalTime> result;
        if(!bindingResult.hasErrors()){
            result = movieService.save(movieDTO);

            if(result.size() == 0){
                re.addFlashAttribute("message", "Successfully!");
            } else {
                re.addFlashAttribute("message", result.toString() + " " + "Time Fail");
            }
            return "redirect:/movie/list-movie";

        }
        if(movieDTO.getMovieId().equals("")){
            return "addMovie";
        }else {
            return "redirect:/movie/edit/" + movieDTO.getMovieId();
        }
    }

    @GetMapping("/movie/list-movie")
    public String listMovieget(@RequestParam @Nullable String currentPage,
                               @RequestParam @Nullable String sizePage,
                               @RequestParam @Nullable String search,
                               Model model) {

        if (currentPage == null) {
            currentPage = "1";
        }

        if (sizePage == null) {
            sizePage = "7";
        }

        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "movieId");

        Page<Movie> contentPage;
        if (search == null || "".equals(search)) {
            search = "";
            contentPage = movieService.findAllPage(pageable);
        } else {
            contentPage = movieService.findByTitle(search, pageable);
        }

        model.addAttribute("search", search);
        model.addAttribute("totalPage", contentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        model.addAttribute("listMovie", contentPage.getContent());
        return "listMovie";
    }

    @GetMapping("/movie/edit/{movieId}")
    public String editMovie(@PathVariable("movieId") String movieId, Model model, RedirectAttributes ra){
        int count = movieService.countByMovieID(movieId);
        if(count == 0){
            ra.addFlashAttribute("message",  "Could not find any Movie with id: " + movieId );
            return "redirect:/movie/list-movie";
        }
        List<CinemaRoom> cinemaRooms = cinemaRoomService.listAll();
        model.addAttribute("cinemaRooms", cinemaRooms);
        Movie movie = movieService.getMovie(movieId);
        MovieDTO movieDTO = movieService.getMovieDTO(movie);
        model.addAttribute("movieDTO",movieDTO);
        List<String> time = new ArrayList<>();
        for(LocalTime localTime : movieDTO.getTimes()){
            time.add(localTime.toString());
        }
        model.addAttribute("time", time);
        model.addAttribute("pageTitle", "Edit Movie (ID: " +  movieId + ")");
        return "addMovie";
    }

    @GetMapping("/movie/delete/{movieId}")
    public String deletePromotion(@PathVariable("movieId") String movieId, RedirectAttributes ra){
        int count = movieService.countByMovieID(movieId);
        if(count != 0){
            movieService.delete(movieId);
            ra.addFlashAttribute("message",  "The Movie ID " + movieId + " has been deleted.");
        }else{
            ra.addFlashAttribute("message",  "Could not find any Movie with id: " + movieId );
        }
        return "redirect:/movie/list-movie";
    }



}
