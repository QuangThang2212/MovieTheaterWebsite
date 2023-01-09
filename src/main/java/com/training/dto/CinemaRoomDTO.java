package com.training.dto;



import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CinemaRoomDTO {


    private Integer roomID;

    @NotBlank(message = "cinemaRoomName cannot empty !")
    private String cinemaRoomName;

    @NotBlank(message = "seatQuantity cannot empty !")
    private String seatQuantity;


}