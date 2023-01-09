package com.training.controller;

import com.training.dto.AllStatusStringDTO;
import com.training.dto.TicketBookingFormResult;
import com.training.entities.*;
import com.training.service.InvoiceService;
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

import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class TicketBookingManagement {
    @Autowired
    private InvoiceService invoiceService;


    @GetMapping("/ticketBookingManagement")
    public String ticketBookingManegementPage(@ModelAttribute("search") String search,
                                              @RequestParam @Nullable String currentPage,
                                              @RequestParam @Nullable String sizePage,
                                              Model model){
        if (currentPage == null) {
            currentPage = "1";
        }

        if (sizePage == null) {
            sizePage = "5";
        }
        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        model.addAttribute("totalPage", 1);
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        return "ticketBookingManagement";
    }
    @GetMapping("/ticketBookingManagement/page")
    public String ticketBookingManegement(Model model,
                                          @ModelAttribute("search") String search,
                                          @RequestParam @Nullable String currentPage,
                                          @RequestParam @Nullable String sizePage){
        if (currentPage == null) {
            currentPage = "1";
        }

        if (sizePage == null) {
            sizePage = "5";
        }

        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "invoiceID");

        Page<Invoice> contentPage;
        if (search == null || "".equals(search)) {
            return "redirect:/ticketBookingManagement";
        } else {
            contentPage = invoiceService.findInvoice(search, pageable);
            if(contentPage.isEmpty()){
                return "redirect:/ticketBookingManagement";
            }
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        model.addAttribute("waitingForTicket", AllStatusStringDTO.waitingForTicket);
        model.addAttribute("getTicket", AllStatusStringDTO.getTicket);
        model.addAttribute("timeFormatter", timeFormatter);
        model.addAttribute("invoices", contentPage.getContent());
        model.addAttribute("totalPage", contentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);

        return "ticketBookingManagement";
    }
    @PostMapping("/ticketBookingManagement/search")
    public String ticketBookingManegementSearch(Model model,
                                                @ModelAttribute("search") String search,
                                                @RequestParam @Nullable String currentPage,
                                                @RequestParam @Nullable String sizePage,
                                                RedirectAttributes ra){
        if (currentPage == null) {
            currentPage = "1";
        }

        if (sizePage == null) {
            sizePage = "5";
        }

        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "invoiceID");
        Page<Invoice> contentPage;
        if (search == null || "".equals(search)) {
            ra.addFlashAttribute("warn",1);
            return "redirect:/ticketBookingManagement";
        } else {
            contentPage = invoiceService.findInvoice(search, pageable);
            if(contentPage.isEmpty()){
                ra.addFlashAttribute("warn",1);
                return "redirect:/ticketBookingManagement";
            }
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        model.addAttribute("waitingForTicket", AllStatusStringDTO.waitingForTicket);
        model.addAttribute("getTicket", AllStatusStringDTO.getTicket);
        model.addAttribute("timeFormatter", timeFormatter);
        model.addAttribute("invoices", contentPage.getContent());
        model.addAttribute("totalPage", contentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);

        return "ticketBookingManagement";
    }
    @GetMapping("/ticketBookingManagement/{id}/getTicket")
    public String ticketBookingGetTicket(Model model, @PathVariable String id){
        Invoice invoice = invoiceService.findById(id);

        model.addAttribute("invoice", invoice);
        model.addAttribute("ticketBookingManage",true);

        return "BookingTicketInfor";
    }
    @GetMapping("/ticketBookingManagement/{id}/waitingForTicket")
    public String ticketBookingWaiting(Model model, @PathVariable String id){
        Invoice invoice = invoiceService.findById(id);

        model.addAttribute("invoice", invoice);
        model.addAttribute("convertDec",new TicketBookingFormResult());
        model.addAttribute("id",id);

        return "employeeBooking";
    }
    @PostMapping("/ticketBookingManagement/{id}/ticketInfor")
    public String bookingTicketInformation(Model model, @PathVariable String id, @ModelAttribute TicketBookingFormResult convertDec){
        Invoice invoice = invoiceService.findById(id);
        if(invoice.getStatus().equals(AllStatusStringDTO.getTicket)){
            model.addAttribute("warn", 1);
            return "BookingTicketInfor";
        }
        if(convertDec.getConvertTicket()!=null && convertDec.getConvertTicket().equalsIgnoreCase(AllStatusStringDTO.agreeConvert)){
            Member member = invoice.getAccount().getMember();
            int limit = (int) (invoice.getTotalMoney()*0.3);
            if(member.getScore()>limit){
                invoice.setTotalMoney(invoice.getTotalMoney() - limit);
                invoice.getAccount().getMember().setScore(member.getScore() - limit);
                invoice.setUseScore(limit);
            }else {
                invoice.setTotalMoney(invoice.getTotalMoney() - member.getScore());
                invoice.setUseScore(member.getScore());
            }
        }

        invoiceService.update(invoice);
        model.addAttribute("invoice", invoice);

        return "BookingTicketInfor";
    }
    @GetMapping("/ticketBookingManagement/{id}/ticketInfor")
    public String bookingTicketInformation(Model model, @PathVariable String id){
        Invoice invoice = invoiceService.findById(id);
        invoiceService.update(invoice);
        model.addAttribute("invoice", invoice);
        return "BookingTicketInfor";
    }
}
