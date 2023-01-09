package com.training.dto;

import com.training.entities.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class InvoiceDTO {
    private String invoiceID;
    private double addscore;
    private LocalDate bookDate;
    private LocalTime bookTime;
    private String status;
    private double totalMoney;
    private double useScore;
    private Account account;
    private Movie movie;
    private List<Ticket> tickets = new ArrayList<>();
    private List<PromotionInvoice> promotionInvoices = new ArrayList<>();
    private List<Schedule_seat> scheduleSeats = new ArrayList<>();
}
