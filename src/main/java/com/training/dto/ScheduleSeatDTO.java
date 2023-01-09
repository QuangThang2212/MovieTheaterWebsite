package com.training.dto;

import com.training.entities.Dates;
import com.training.entities.Invoice;
import com.training.entities.Schedule;
import com.training.entities.Seat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
public class ScheduleSeatDTO {
    private String ScheduleSeatID;
    private String status;
    private Invoice invoice;
    private Schedule schedule;
    private Seat seat;
    private Dates dates;
}
