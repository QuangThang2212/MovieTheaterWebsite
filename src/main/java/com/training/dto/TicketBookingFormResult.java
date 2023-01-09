package com.training.dto;

import com.training.entities.Seat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class TicketBookingFormResult {
    private List<Integer> seats = new ArrayList<>();
    private String convertTicket;
}
