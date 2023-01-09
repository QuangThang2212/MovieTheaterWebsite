package com.training.controller;

import com.training.dto.AllStatusStringDTO;
import com.training.dto.ConfirmObjectDTO;
import com.training.dto.TicketBookingFormResult;
import com.training.entities.*;
import com.training.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SellingTicketController {
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
    private ConfirmObjectDTO confirmObjectDTO;

    @GetMapping("/showtime/{date}/{movieID}/{time}/confirm/{seatsSelec}/employee")
    public String employeeConfirmPage(@PathVariable StringBuffer seatsSelec, @PathVariable String movieID, @PathVariable String date, @PathVariable int time, Model model){
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
        confirmObjectDTO = new ConfirmObjectDTO(movie,dates,schedule,selecResult,seats,total, AllStatusStringDTO.sellingInvoice, 0);

        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        model.addAttribute("noSearch", true);
        return "employeeSelling";
    }

    @PostMapping("/showtime/confirm/employee/find")
    public String searchMember(@ModelAttribute("seachInput") String seachInput, Model model){
        System.out.println(seachInput);
        Account account = accountService.findByAccountIDOrIdentityCardAndRoleName(seachInput, AllStatusStringDTO.memberRole);
        if(account==null){
            model.addAttribute("confirmObjectDTO", confirmObjectDTO);
            model.addAttribute("warn",1);
            return "employeeSelling";
        }
        confirmObjectDTO.setAccount(account);
        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        model.addAttribute("convertDec",new TicketBookingFormResult());
        return "employeeSelling";
    }
    @GetMapping("/showtime/confirm/{userName}/employee/ticketInfor")
    public String sellingTicketNoScore(Model model, @PathVariable String userName){
        Account account = accountService.getByUserName(userName);
        confirmObjectDTO.setAccount(account);
        confirmObjectDTO.setConvertScore(0);
        String message = invoiceService.save(confirmObjectDTO);
        if(message.equals("sold")){
            model.addAttribute("warn", 1);
            model.addAttribute("message", "Booking fail! (the seat has been booked few second ago)");
            return "memberTicketInformation";
        }
        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        return "sellingTicketInfor";
    }
    @GetMapping("/showtime/confirm/employee/noAccount/ticketInfor")
    public String sellingTicketNoAccount(Model model, Principal principal){
        Account account = accountService.getByUserName(principal.getName());
        confirmObjectDTO.setAccount(account);
        confirmObjectDTO.setConvertScore(0);

        String message = invoiceService.save(confirmObjectDTO);
        if(message.equals("sold")){
            model.addAttribute("warn", 1);
            model.addAttribute("message", "Booking fail! (the seat has been booked few second ago)");
            return "memberTicketInformation";
        }
        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        return "sellingTicketInfor";
    }
    @PostMapping("/showtime/confirm/{userName}/employee/ticketInfor")
    public String sellingTicketInformation(@PathVariable String userName, Model model, @ModelAttribute TicketBookingFormResult convertDec){


        Account account = accountService.getByUserName(userName);
        confirmObjectDTO.setAccount(account);
        if(convertDec.getConvertTicket().equalsIgnoreCase(AllStatusStringDTO.agreeConvert)){
            Member member = confirmObjectDTO.getAccount().getMember();
            int limit = (int) (confirmObjectDTO.getTotal()*0.3);
            if(member.getScore()>limit){
                confirmObjectDTO.setTotal(confirmObjectDTO.getTotal() - limit);
                System.out.println(confirmObjectDTO.getTotal());
                confirmObjectDTO.getAccount().getMember().setScore(member.getScore() - limit);
                System.out.println(confirmObjectDTO.getAccount().getMember().getScore());
                confirmObjectDTO.setConvertScore(limit);
            }else {
                confirmObjectDTO.setTotal(confirmObjectDTO.getTotal() - member.getScore());
                confirmObjectDTO.setConvertScore(member.getScore());
            }
        }

        String message = invoiceService.save(confirmObjectDTO);
        if(message.equals("sold")){
            model.addAttribute("warn", 1);
            model.addAttribute("message", "Booking fail! (the seat has been booked few second ago)");
            return "memberTicketInformation";
        }
        model.addAttribute("confirmObjectDTO", confirmObjectDTO);
        return "sellingTicketInfor";
    }
}
