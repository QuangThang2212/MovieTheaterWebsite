package com.training.controller;

import com.training.dto.PromotionDTO;
import com.training.entities.Promotion;
import com.training.service.PromotionService;
import com.training.util.PromotionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/promotion")
    public String contentPage(@RequestParam @Nullable String currentPage,
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
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "promotionId");

        Page<Promotion> contentPage;
        if (search == null || "".equals(search)) {
            search = "";
            contentPage = promotionService.findAll(pageable);
        } else {
            contentPage = promotionService.findByTitle(search, pageable);
        }

        model.addAttribute("search", search);
        model.addAttribute("totalPage", contentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        model.addAttribute("listPromotion", contentPage.getContent());
        return "promotion";
    }
    @GetMapping("/promotion/create")
    public String createPromotion(Model model){
        model.addAttribute("promotionDTO", new PromotionDTO());
        model.addAttribute("pageTitle", "Add New Promotion");
        return "promotion_form";
    }

    @PostMapping("/promotion/add")
    public String addPromotion(@ModelAttribute @Valid PromotionDTO promotionDTO, RedirectAttributes ra){
        promotionService.save(promotionDTO);
        ra.addFlashAttribute("message", "The promotion has been saved successfully !");
        return "redirect:/promotion";
    }

    @GetMapping("/promotion/edit/{promotionId}")
    public String editPromotion(@PathVariable("promotionId") Integer promotionId, Model model, RedirectAttributes ra) throws PromotionNotFoundException {
        try{
            Promotion promotion = promotionService.get(promotionId);
            model.addAttribute("promotionDTO", promotion);
            model.addAttribute("pageTitle", "Edit Promotion (ID: " +  promotionId + ")");
            return "/promotion_form";
        }catch(PromotionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/promotion";
        }
    }

    @GetMapping("/promotion/delete/{promotionId}")
    public String deletePromotion(@PathVariable("promotionId") Integer promotionId, RedirectAttributes ra) throws PromotionNotFoundException{
        try{
            promotionService.delete(promotionId);
            ra.addFlashAttribute("message",  "The Promotion ID " + promotionId + " has been deleted.");
        }catch(PromotionNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/promotion";
    }
}
