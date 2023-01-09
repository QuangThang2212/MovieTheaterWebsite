package com.training.service.impl;

import com.training.dto.MovieDTO;
import com.training.entities.*;
import com.training.repository.*;
import com.training.service.CinemaRoomService;
import com.training.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;

    @Autowired
    private DatesReponsitory datesReponsitory;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TypeRepository typeRepository;

//    @Autowired
//    private MovieScheduleRepository movieScheduleRepository;

    private String nametag = "TheMovie--";


    @Transactional
    public Set<LocalTime> save(MovieDTO movieDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Movie movie;

        int result = 0;
        if(!movieDTO.getMovieId().contains(nametag)){
            movie = new Movie();
            movie = modelMapper.map(movieDTO,Movie.class);
            Long id = movieRepository.count();
            id++;
            movie.setMovieId(nametag.concat(String.valueOf(id)));

        }else {
            result++;
            movie = movieRepository.findByMovieId(movieDTO.getMovieId());
            movie = modelMapper.map(movieDTO,Movie.class);
            movieRepository.deletebyMovieID(movie.getMovieId());
            movieRepository.deleteDatebyMovieID(movie.getMovieId());
            movieRepository.deleteTypebyMovieID(movie.getMovieId());
        }

        CinemaRoom cinemaRoom = cinemaRoomRepository.findByRoomID(movieDTO.getRoom());
        movie.setCinemaRoom(cinemaRoom);

        List<String> types = movieDTO.getType();

        for(String type : types){
            Optional<Type> optionalType = typeRepository.findByTypeName(type);
            Type typeInput = optionalType.orElse(new Type());
            typeInput.setTypeName(type);
            MovieType movieType = new MovieType(movie,typeInput);
            movie.getMovieTypes().add(movieType);
        }


        LocalDate dateStart = movieDTO.getFromDate();
        LocalDate dateEnd = movieDTO.getToDate();

        boolean check =true;
        List<LocalTime> time = movieDTO.getTimes();
        Set<LocalTime> timeFail = new HashSet<>();


        while(check){
            List<Movie> movies = movieRepository.findListMovieByDate(dateStart , movieDTO.getRoom());
            List<LocalTime> list = movieRepository.finTimeByListMovie(movies, movieDTO.getRoom());

            for(LocalTime localTime : time){
                if(list.contains(localTime)){
                    timeFail.add(localTime);
                }
            }

            for(Movie movieChek : movies){
                List<LocalTime> movieCheckTime = movieRepository.finTimeByMovieID(movieChek.getMovieId(),movie.getCinemaRoom().getRoomID());
                for(LocalTime localTime : movieCheckTime){
                    LocalTime movieCTDuration = localTime.plusMinutes(Long.valueOf(movieChek.getDuration() +10));
                    for(LocalTime localTimecheck : time){
                        LocalTime localTCDuration = localTimecheck.plusMinutes(Long.valueOf(movieDTO.getDuration() +10));
                        if(!(localTimecheck.isBefore(localTime) && localTCDuration.isBefore(localTime)) && !(localTimecheck.isAfter(movieCTDuration) && localTCDuration.isAfter(movieCTDuration))){
                            timeFail.add(localTimecheck);
                        }
                    }

                }
            }

            Optional<Dates> dates = datesReponsitory.findByShowDate(dateStart);
            Dates date = dates.orElse(new Dates());
            date.setShowDate(dateStart);
            DayOfWeek dayOfWeek = dateStart.getDayOfWeek();
            date.setDateName(dayOfWeek.name().substring(0,3));
            MovieDate movieDate = new MovieDate(movie,date);
            movie.getMovieDates().add(movieDate);
            if(dateStart.isEqual(dateEnd)){
                check = false;
            }
            dateStart=dateStart.plusDays(1);
        }
        for (LocalTime localTime: timeFail){
            time.remove(localTime);
        }

        if(time.isEmpty()){
            movieRepository.save(movie);
            return timeFail;
        }

        LocalTime checkTime = time.get(0);
        for(int i = 0 ; i< time.size(); i++){
            if(i == 0){
                continue;
            }else {
                if(!checkTime.plusMinutes(Long.valueOf(movie.getDuration() + 10)).isBefore(time.get(i))){
                    timeFail.add(time.get(i));
                }else{
                    checkTime = time.get(i);
                }
            }
        }
        for (LocalTime localTime: timeFail){
            time.remove(localTime);
        }
        for(LocalTime localTime : time){
            Optional<Schedule> schedules = scheduleRepository.findByScheduleTime(localTime);
            Schedule schedule = schedules.orElse(new Schedule());
            schedule.setScheduleTime(localTime);
            scheduleRepository.save(schedule);
            MovieSchedule movieSchedule = new MovieSchedule(movie, schedule);
            movie.getMovieSchedules().add(movieSchedule);
        }

        movieRepository.save(movie);
        return timeFail;
    }

    @Override
    public List<Movie> findAll() {

        return movieRepository.findAll();
    }

    @Override
    public Movie getMovie(String movieID) {

        return movieRepository.findByMovieId(movieID);
    }

    @Override
    public Movie findById(String string) {
        return movieRepository.findByMovieId(string);
    }

    @Override
    public List<LocalTime> findTimeByMovieId(String movieId) {
        return scheduleRepository.findbyMovieID(movieId);
    }

    @Override
    public List<String> findTypeByMovieID(String moviId) {
        return typeRepository.findTypeNameMovieID(moviId);
    }

    @Override
    public MovieDTO getMovieDTO(Movie movie) {
        ModelMapper modelMapper = new ModelMapper();
        MovieDTO movieDTO = modelMapper.map(movie,MovieDTO.class);
        List<LocalTime> times = findTimeByMovieId(movie.getMovieId());
        movieDTO.setTimes(times);
        List<String> type = findTypeByMovieID(movie.getMovieId());
        movieDTO.setType(type);
        return movieDTO;
    }

    @Override
    public List<Movie> findAllByDate(LocalDate date) {
        return movieRepository.findAllByDate(date);
    }

    @Transactional
    public void delete(String movieID) {
        movieRepository.deletebyMovieID(movieID);
        movieRepository.deleteDatebyMovieID(movieID);
        movieRepository.deleteTypebyMovieID(movieID);
        movieRepository.deleteById(movieID);
    }

    @Override
    public Integer countByMovieID(String movieID) {
        return movieRepository.countByMovieId(movieID);
    }

    @Override
    public Page<Movie> findAllPage(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Page<Movie> findByTitle(String search, Pageable pageable) {
        return movieRepository.findByNameEnglishContaining(search,pageable);
    }
}


