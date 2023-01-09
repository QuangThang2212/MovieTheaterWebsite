package com.training.controller;

import com.training.dto.AccountDTO;
import com.training.dto.AllStatusStringDTO;
import com.training.dto.HistoryScoreDTO;
import com.training.entities.Account;
import com.training.entities.Invoice;
import com.training.service.AccountService;
import com.training.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private InvoiceService invoiceService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/403")
    public String roleCheck(){
        return "redirect:/login?noRole";
    }

    @GetMapping ("/user")
    public String userProfile(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        Account account = accountService.getByUserName(user.getUsername());
        model.addAttribute("account", account);
        return "userProfile";
    }

    @GetMapping ("/user/history")
    public String userProfileHistory(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        Account account = accountService.getByUserName(user.getUsername());
        model.addAttribute("account", account);
        model.addAttribute("historyScoreDTO", new HistoryScoreDTO());
        return "userViewHistoryScore";
    }

    @GetMapping ("/user/bookedTicket")
    public String userBookedTicket(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        Account account = accountService.getByUserName(user.getUsername());
        model.addAttribute("account", account);
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        for(Invoice invoice : invoiceService.findByAccount(account)){
            if(invoice.getStatus().equals(AllStatusStringDTO.waitingForTicket) || invoice.getStatus().equals(AllStatusStringDTO.getTicket)){
                invoiceList.add(invoice);
            }
        }
        model.addAttribute("listInvoice", invoiceList);
        return "userViewBookedTicket";
    }

    @GetMapping ("/user/canceledTicket")
    public String userCanceledTicket(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        Account account = accountService.getByUserName(user.getUsername());
        model.addAttribute("account", account);
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        for(Invoice invoice : invoiceService.findByAccount(account)){
            if(invoice.getStatus().equals(AllStatusStringDTO.cancelTicket)){
                invoiceList.add(invoice);
            }
        }
        model.addAttribute("listInvoice", invoiceList);
        return "userViewCanceledTicket";
    }

    @GetMapping("/user/edit")
    public String userEditProfile(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        Account account = accountService.getByUserName(user.getUsername());
        model.addAttribute("account", account);
        model.addAttribute("accountDTO", new AccountDTO());
        return "userEditProfile";
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("accountDTO", new AccountDTO());
        return "register";
    }

    @GetMapping("/login")
    public String loginGet(){
        return "login";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute AccountDTO accountDTO, Model model, BindingResult bindingResult){
        if (accountService.findByUserName(accountDTO) == true && accountDTO.getUserName() != null) {
            bindingResult.addError(new FieldError("accountDTO", "userName", accountDTO.getUserName(), false, null, null, "Your user name already existed"));
        }
        if (accountService.findByIdentityCard(accountDTO) == true && accountDTO.getIdentityCard() != null) {
            bindingResult.addError(new FieldError("accountDTO", "identityCard", accountDTO.getIdentityCard(), false, null, null, "Your identity card already existed"));
        }
        if (accountService.findByEmail(accountDTO) == true && accountDTO.getEmail() != null) {
            bindingResult.addError(new FieldError("accountDTO", "email", accountDTO.getEmail(), false, null, null, "Your email already existed"));
        }
        if (accountService.findByPhone(accountDTO) == true && accountDTO.getPhoneNumber() != null) {
            bindingResult.addError(new FieldError("accountDTO", "phoneNumber", accountDTO.getPhoneNumber(), false, null, null, "Your phone number already existed"));
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("accountDTO", accountDTO);
            return "register";
        }
        accountService.save(accountDTO, "ROLE_USER");
        return "redirect:/login?successRegister";
    }

    @PostMapping("/user/edit")
    public String userEdit(@Valid @ModelAttribute AccountDTO accountDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Account account = accountService.getByUserName(user.getUsername());
        accountService.update(account, accountDTO);
        return "redirect:/user?updateSuccessfully";
    }

    @PostMapping ("/user/history")
    public String userProfileHistorySearch(Authentication authentication, Model model, @ModelAttribute HistoryScoreDTO historyScoreDTO){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userName", user.getUsername());
        Account account = accountService.getByUserName(user.getUsername());
        model.addAttribute("account", account);
        model.addAttribute("listInvoice", invoiceService.findByAccount(account, historyScoreDTO));
        System.out.println(invoiceService.findByAccount(account, historyScoreDTO));
        return "userViewHistoryScore";
    }


}
