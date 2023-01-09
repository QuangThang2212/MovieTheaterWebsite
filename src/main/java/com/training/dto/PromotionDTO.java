package com.training.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class PromotionDTO {

    private Integer promotionId;
    @NotBlank(message = "Title cannot empty !")
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
    @Min(value = 1, message = "Discount must be greater than 0 !")
    @Max(value = 100, message = "Discount must be less than 100 !")
    private int discountLevel;
    private String detail;
    private String image;


}
