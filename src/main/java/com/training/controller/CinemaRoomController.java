package com.training.controller;

import com.training.dto.AllStatusStringDTO;
import com.training.dto.CinemaRoomDTO;
import com.training.dto.TicketBookingFormResult;
import com.training.entities.CinemaRoom;
import com.training.entities.Schedule_seat;
import com.training.entities.Seat;
import com.training.repository.CinemaRoomRepository;
import com.training.service.AccountService;
import com.training.service.CinemaRoomService;
import com.training.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Controller
public class CinemaRoomController {

    @Autowired
    private CinemaRoomService cinemaRoomService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/cinemaRoom")
    public String contentPage(@RequestParam @Nullable String currentPage,
                              @RequestParam @Nullable String sizePage,
                              Model model) {
        if (currentPage == null || currentPage == "NaN") {
            currentPage = "1";
        }

        if (sizePage == null || currentPage == "NaN") {
            sizePage = "5";
        }

        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "roomID");

        Page<CinemaRoom> contentPage;
        contentPage = cinemaRoomService.findAll(pageable);

        model.addAttribute("listCinemaRoom", contentPage.getContent());
        model.addAttribute("totalPage", contentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        return "cinemaRoom";
    }
    @PostMapping("/cinemaRoom")
    public String contentPageSearch(@RequestParam @Nullable String currentPage,
                              @RequestParam @Nullable String sizePage,
                              @RequestParam @Nullable String search,
                              Model model) {
        if (currentPage == null) {
            currentPage = "1";
        }

        if (sizePage == null) {
            sizePage = "5";
        }

        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "roomID");

        Page<CinemaRoom> contentPage;
        if (search == null || "".equals(search)) {
            search = "";
            contentPage = cinemaRoomService.findAll(pageable);
        } else {
            contentPage = cinemaRoomService.findByCinemaRoomNameContaining(search, pageable);
            if(contentPage.isEmpty()){
                model.addAttribute("warn",1);
            }
        }
        model.addAttribute("listCinemaRoom", contentPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("totalPage", contentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        return "cinemaRoom";
    }


    @GetMapping("/cinemaRoom/create")
    public String createCinemaRoom(Model model) {
        model.addAttribute("cinemaRoomDTO", new CinemaRoomDTO());
        model.addAttribute("pageTitle", "Add New CinemaRoom");
        return "createCinemaRoom";
    }

    @PostMapping("/cinemaRoom/add")
    public String addCinemaRoom(@ModelAttribute CinemaRoomDTO cinemaRoomDTO, RedirectAttributes ra, Model model) {
        CinemaRoom cinemaRoom = cinemaRoomService.findByCinemaRoomName(cinemaRoomDTO.getCinemaRoomName());
        if(cinemaRoom != null){
            model.addAttribute("cinemaRoomName_mess","CinemaRoom already exsist");
            return "createCinemaRoom";
        }
        cinemaRoomService.save(cinemaRoomDTO);

        ra.addFlashAttribute("message", "The cinemaRoom has been saved successfully !");
        return "redirect:/cinemaRoom";
    }

    @GetMapping("/cinemaRoom/seatCinemaRoom/{id}/{name}")
    public String editCinemaRoom(@PathVariable("id") Integer roomID, Model model,
                                 RedirectAttributes ra, @PathVariable("name")String name) {
        List<Seat> vipSeat = seatService.findAllByRoomIDAndSeatType(roomID, AllStatusStringDTO.vipSeatStatus);
        List<Seat> normalSeat = seatService.findAllByRoomIDAndSeatType(roomID, AllStatusStringDTO.normalSeatStatus);
        Map<Seat, String> seatStringMap = new LinkedHashMap<>();

        for(Seat seat: normalSeat){
            seatStringMap.put(seat, AllStatusStringDTO.normalStatus);
        }
        for(Seat seat: vipSeat){
            seatStringMap.put(seat, AllStatusStringDTO.vipStatus);
        }
        TreeMap<Seat, String> sorted = new TreeMap<>();
        sorted.putAll(seatStringMap);

        model.addAttribute("cinemaRoomName", name);
        model.addAttribute("seatStringMap", sorted);
        model.addAttribute("ListOfSelectSeatDTO", new TicketBookingFormResult());
        model.addAttribute("vipStatus", AllStatusStringDTO.vipStatus);
        model.addAttribute("normalStatus", AllStatusStringDTO.normalStatus);
        return "seatCinemaRoom";
    }

    @PostMapping ("/cinemaRoom/seatDetailUpdate")
    public String seatCinemaRoom(Model model, @ModelAttribute TicketBookingFormResult ListOfSeat, RedirectAttributes ra){
        List<Integer> seats = ListOfSeat.getSeats();
        seatService.updateListOfSeat(seats);

        ra.addFlashAttribute("message", "The cinemaRoom has been update successfully !");
        return "redirect:/cinemaRoom";
    }

}