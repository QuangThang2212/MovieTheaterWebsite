package com.training.service.impl;

import com.training.dto.*;
import com.training.entities.*;
import com.training.repository.InvoiceRepository;
import com.training.repository.MemberRepository;
import com.training.repository.SeatScheduleRepository;
import com.training.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private SeatScheduleRepository seatScheduleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public String save(ConfirmObjectDTO confirmObjectDTO) {
        ModelMapper modelMapper = new ModelMapper();

        int number = invoiceRepository.findByAccount(confirmObjectDTO.getAccount()).size();

        Invoice invoice = new Invoice();
        invoice.setMovie(confirmObjectDTO.getMovie());
        invoice.setAccount(confirmObjectDTO.getAccount());
        invoice.setBookDate(LocalDate.now());
        invoice.setBookTime(LocalTime.now());
        invoice.setAddscore(100*confirmObjectDTO.getSeats().size());
        invoice.setTotalMoney(confirmObjectDTO.getTotal());
        invoice.setUseScore(confirmObjectDTO.getConvertScore());
        invoice.setInvoiceID(confirmObjectDTO.getAccount().getAccountID()+"-N"+number+"-"+confirmObjectDTO.getMovie().getMovieId()+"-"+invoice.getBookDate());

        if(confirmObjectDTO.getStatus().equals(AllStatusStringDTO.bookingInvoice)){
            invoice.setStatus(AllStatusStringDTO.waitingForTicket);
        }else if(confirmObjectDTO.getStatus().equals(AllStatusStringDTO.sellingInvoice)){
            invoice.setStatus(AllStatusStringDTO.getTicket);
        }

        invoiceRepository.save(invoice);

        Schedule_seat schedule_seat;
        List<ScheduleSeatDTO> scheduleSeatDTOList = new ArrayList<>();
        ScheduleSeatDTO scheduleSeatDTO;
        for(Seat seat: confirmObjectDTO.getSeats()){
            schedule_seat = seatScheduleRepository.findByDatesAndScheduleAndSeat(confirmObjectDTO.getDates().getId(),
                                                                                confirmObjectDTO.getSchedule().getId(),
                                                                                seat.getSeatId());
            if(schedule_seat!=null){
                System.out.println("scheduleSeat exist");
                invoiceRepository.delete(invoice);
                return "sold";
            } else {
                scheduleSeatDTO = new ScheduleSeatDTO();
                scheduleSeatDTO.setScheduleSeatID(  seat.getSeatColumn()+seat.getSeatRow()+
                                                    "-"+confirmObjectDTO.getMovie().getCinemaRoom().getRoomID()+
                                                    "-"+confirmObjectDTO.getDates().getShowDate()+
                                                    "-"+confirmObjectDTO.getSchedule().getScheduleTime());
                scheduleSeatDTO.setSchedule(confirmObjectDTO.getSchedule());
                scheduleSeatDTO.setDates(confirmObjectDTO.getDates());
                scheduleSeatDTO.setInvoice(invoice);
                scheduleSeatDTO.setSeat(seat);
                scheduleSeatDTO.setStatus(AllStatusStringDTO.scheduleSeatStatus);
                scheduleSeatDTOList.add(scheduleSeatDTO);
            }
        }
        Member member = confirmObjectDTO.getAccount().getMember();
        if(confirmObjectDTO.getStatus().equals(AllStatusStringDTO.sellingInvoice)){
            System.out.println(member.getScore());
            member.setScore(member.getScore()+invoice.getAddscore());
            memberRepository.save(member);
        }
        Schedule_seat scheduleSeat;
        for(ScheduleSeatDTO ab: scheduleSeatDTOList){
            scheduleSeat = modelMapper.map(ab, Schedule_seat.class);
            seatScheduleRepository.save(scheduleSeat);
        }
        return "success";
    }

    @Override
    public String update(Invoice invoice) {
        invoice.setStatus(AllStatusStringDTO.getTicket);
        Member member = invoice.getAccount().getMember();
        if(invoice.getUseScore()!=0){
            member.setScore(member.getScore());
        }
        member.setScore(member.getScore()+invoice.getAddscore());
        invoiceRepository.save(invoice);
        memberRepository.save(member);
        return "success";
    }

    @Override
    public List<Invoice> findByAccount(Account account) {
        List<Invoice> invoices = invoiceRepository.findByAccount(account);
        return invoices;
    }

    @Override
    public List<Invoice> findByAccount(Account account, HistoryScoreDTO historyScoreDTO) {
        List<Invoice> invoiceList = new ArrayList<>();
        for(Invoice invoice : invoiceRepository.findByAccount(account)){
            if(invoice.getBookDate().compareTo(historyScoreDTO.getFromDate()) >= 0 &&
                    invoice.getBookDate().compareTo(historyScoreDTO.getToDate()) <= 0){
                invoiceList.add(invoice);
            }
        }
        return invoiceList;
    }

    @Override
    public Page<Invoice> findInvoice(String result, Pageable pageable) {
        return invoiceRepository.findInvoice(result, pageable);
    }

    @Override
    public Invoice findById(String id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice.orElse(null);
    }

}
