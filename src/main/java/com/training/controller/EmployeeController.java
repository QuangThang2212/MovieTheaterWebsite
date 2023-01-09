package com.training.controller;

import com.training.dto.AccountDTO;
import com.training.dto.EmployeeDTO;
import com.training.entities.Employee;
import com.training.service.AccountService;
import com.training.service.EmployeeService;
import com.training.util.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/employee/viewEmployeeList")
    public String contentPage(Model model,
                              @RequestParam @Nullable String currentPage,
                              @RequestParam @Nullable String sizePage,
                              @RequestParam @Nullable String search) {

        if (currentPage == null) {
            currentPage = "1";
        }

        if (sizePage == null) {
            sizePage = "7";
        }

        int page = Integer.parseInt(currentPage);
        int size = Integer.parseInt(sizePage);
        Pageable pageable = PageRequest.of(page -1, size, Sort.Direction.ASC,"employeeID");

        Page<Employee> employeePage;
        if (search == null || "".equals(search)) {
            search = "";
            employeePage = employeeService.findAll(pageable);
        } else {
            employeePage = employeeService.findByemployeeID(search, pageable);
        }

        List<Employee> employeeList = employeeService.listAll();
        model.addAttribute("search",search);
        model.addAttribute("totalPage", employeePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        model.addAttribute("employeeList", employeePage.getContent());

        return "viewListEmployee";
    }

    @GetMapping("/employee/EmployeeRegister")
    public String addEmployeeGet(Model model) {
        model.addAttribute("accountDTO", new AccountDTO());
        return "registerEmployee";
    }

    @PostMapping("/employee/EmployeeRegister")
    public String createEmployee(@ModelAttribute @Valid AccountDTO accountDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Register employee have erorrs");
            return "registerEmployee";
        }
        accountService.save(accountDTO, "ROLE_EMPLOYEE");
        redirectAttributes.addFlashAttribute("message","Employee saved successfully");
        return "redirect:/employee/viewEmployeeList";
    }

    @GetMapping("/employee/edit/{employeeID}")
    public String editEmployee(@PathVariable("employeeID") String employeeID, ModelMap model,
                                   RedirectAttributes redirectAttributes) throws EmployeeNotFoundException {
        try {
            Optional<Employee> employee = employeeService.findById(employeeID);
            model.addAttribute("accountDTO", new AccountDTO());
            return "/registerEmployee";
        } catch (EmployeeNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/employee/viewEmployeeList";
        }
    }

    @GetMapping("/employee/delete/{employeeID}")
    public String deleteEmployee(@PathVariable("employeeID") String employeeID,
                                 RedirectAttributes redirectAttributes) throws EmployeeNotFoundException{

        System.out.println("get in here 1");
        employeeService.deleteById(employeeID);
        System.out.println("get in here 2");
        redirectAttributes.addFlashAttribute("message","The User which have ID =  : " + employeeID + "has been deleted");

        return "redirect:/employee/viewEmployeeList";
    }
}
