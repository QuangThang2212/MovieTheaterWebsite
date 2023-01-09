package com.training.controller;

import com.training.dto.TicketBookingFormResult;
import com.training.dto.AllStatusStringDTO;
import com.training.entities.*;
import com.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
public class SelectionSeatController {
    @Autowired
    private SeatScheduleService seatScheduleService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/showtime/{date}/{movieID}/{time}")
    public String selecSeatPage(@PathVariable(value = "movieID") String movieID, @PathVariable int time, @PathVariable String date, Model model) {

        List<Schedule_seat> scheduleSeats = seatScheduleService.listOfSeatByMovieDateSchedule(movieID, LocalDate.parse(date), time);
        List<Seat> vipSeat = seatService.findAllByMovieIDAndSeatType(movieID, AllStatusStringDTO.vipSeatStatus);
        List<Seat> normalSeat = seatService.findAllByMovieIDAndSeatType(movieID, AllStatusStringDTO.normalSeatStatus);

        //list of available number of seat which you can choose
        int availableSeat = vipSeat.size() + normalSeat.size() - scheduleSeats.size();

        Map<Seat, String> seatStringMap = new LinkedHashMap<>();

        for(Seat seat: normalSeat){
            seatStringMap.put(seat, AllStatusStringDTO.normalStatus);
        }
        for(Seat seat: vipSeat){
            seatStringMap.put(seat, AllStatusStringDTO.vipStatus);
        }
        int count=0;
        for(Map.Entry<Seat,String> seat: seatStringMap.entrySet()){
            for(Schedule_seat schedule_seat : scheduleSeats){
                if(seat.getKey().getSeatId() == schedule_seat.getSeat().getSeatId()){
                    seatStringMap.put(seat.getKey(), AllStatusStringDTO.soldStatus);
                    count++;
                    break;
                }
            }
            if(count==scheduleSeats.size()){
                break;
            }
        }

        TreeMap<Seat, String> sorted = new TreeMap<>();
        sorted.putAll(seatStringMap);

        model.addAttribute("ListOfSelectSeatDTO", new TicketBookingFormResult());
        model.addAttribute("dateSlec", date);
        model.addAttribute("mopvieID", movieID);
        model.addAttribute("time", time);
        model.addAttribute("normalStatus", AllStatusStringDTO.normalStatus);
        model.addAttribute("vipStatus", AllStatusStringDTO.vipStatus);
        model.addAttribute("soldStatus", AllStatusStringDTO.soldStatus);
        model.addAttribute("seatStringMap",sorted);
        model.addAttribute("availableSeat", availableSeat);

        return "Selec_seat";
    }

    @PostMapping("/showtime/{date}/{movieID}/{time}/confirm")
    public String selecSeatPost(@ModelAttribute TicketBookingFormResult ticketBookingFormResult, @PathVariable String date, @PathVariable String movieID, @PathVariable String time, Principal principal){
        Account account = accountService.getByUserName(principal.getName());
        StringBuffer seatsSelec = new StringBuffer();
        for(int a: ticketBookingFormResult.getSeats()){
            seatsSelec.append(a+",");
        }
        boolean check =false;
        for(AccountRole accountRole: account.getAccountRoles()){
            if(accountRole.getRole().getRoleName().equals(AllStatusStringDTO.employeeRole)){
                check=true;
                break;
            }
        }
        if(check){
            return "redirect:/showtime/"+date+"/"+movieID+"/"+time+"/confirm/"+seatsSelec+"/employee";
        }
        return "redirect:/showtime/"+date+"/"+movieID+"/"+time+"/confirm/"+seatsSelec+"/"+principal.getName()+"/member";
    }
}
