package com.training.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Invoice implements Serializable {

    @Id
    private String invoiceID;

    private double addscore;
    @Column(nullable = false)
    private LocalDate bookDate;
    @Column(nullable = false)
    private LocalTime bookTime;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private double totalMoney;
    @Column(nullable = false)
    private double useScore;

    @ManyToOne
    @JoinColumn(name = "account_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "movie_ID")
    private Movie movie;

    @OneToMany(mappedBy = "invoice")
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "invoice")
    private List<PromotionInvoice> promotionInvoices = new ArrayList<>();

    @OneToMany(mappedBy = "invoice")
    private List<Schedule_seat> scheduleSeats = new ArrayList<>();
}
