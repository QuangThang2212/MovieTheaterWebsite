package com.training.controller;

import com.training.dto.AllStatusStringDTO;
import com.training.dto.ConfirmObjectDTO;
import com.training.entities.*;
import com.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ConfirmTicketController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private DatesService datesService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private InvoiceService invoiceService;
    private static ConfirmObjectDTO confirmObjectDTO;

    @GetMapping("/showtime/{date}/{movieID}/{time}/confirm/{seatsSelec}/{userName}/member")
    public String memberConfirmPage(@PathVariable StringBuffer seatsSelec, @PathVariable String movieID, @PathVariable String date, @PathVariable int time, @PathVariable String userName, Model model){
        Account account = accountService.getByUserName(userName);
        Movie movie = movieService.getMovie(movieID);
        Dates dates = datesService.findByShowDate(LocalDate.parse(date));
        Schedule schedule = scheduleService.findById(time);

        String result= seatsSelec.toString();
        String[] seat = result.split(",");
        List<Seat> seats = new ArrayList<>();
        for(String a: seat){
            seats.add(seatService.findById(Integer.parseInt(a)));
        }
        double total = 0;
        StringBuffer selecResult = new StringBuffer();
        for(Seat seatR: seats) {
            selecResult.append(seatR.getSeatColumn()+seatR.getSeatRow()+" ");
            total = total + seatR.getSeatType().getPrice();
        }

        confirmObjectDTO = new ConfirmObjectDTO(account,movie,dates,schedule, selecResult,seats,total, AllStatusStringDTO.bookingInvoice, 0);

        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        return "memberBooking";
    }
    @GetMapping("/showtime/confirm/member/ticketInfor")
    public String ticketInformation(Model model){
        String message = invoiceService.save(confirmObjectDTO);
        if(message.equals("sold")){
            model.addAttribute("warn", 1);
            model.addAttribute("message", "Booking fail! (the seat has been booked few second ago)");
            return "memberTicketInformation";
        }

        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        return "memberTicketInformation";
    }

}
