package com.training.service.impl;

import com.training.dto.AllStatusStringDTO;
import com.training.entities.CinemaRoom;
import com.training.entities.Seat;
import com.training.entities.SeatType;
import com.training.repository.SeatRepository;
import com.training.repository.SeatTypeRepository;
import com.training.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Override
    public Seat findById(int ID) {
        Optional<Seat> seat = seatRepository.findById(ID);
        return seat.orElse(null);
    }

    @Override
    public List<Seat> findAllByMovieIDAndSeatType(String movieID, String seatType) {
        return seatRepository.findAllByMovieIDAndSeatType(movieID, seatType);
    }

    @Override
    public List<Seat> findAllByRoomIDAndSeatType(int roomid, String seatType) {
        return seatRepository.findAllByRoomIDAndSeatType(roomid,seatType);
    }

    @Override
    public List<Seat> findAllSeatByInvoiceID(String id) {
        return seatRepository.findAllSeatByInvoiceID(id);
    }

    @Override
    public String updateListOfSeat(List<Integer> seats) {
        List<Seat> seatList=new ArrayList<>();
        Seat result;
        for (int a: seats){
            result = seatRepository.findById(a).get();
            seatList.add(result);
        }
        Optional<SeatType> vip = seatTypeRepository.findBySeatType(AllStatusStringDTO.vipSeatStatus);
        Optional<SeatType> normal = seatTypeRepository.findBySeatType(AllStatusStringDTO.normalSeatStatus);
        if (!vip.isPresent()){
            SeatType seatType = new SeatType();
            seatType.setSeatType(AllStatusStringDTO.vipSeatStatus);
            seatType.setPrice(AllStatusStringDTO.vipSeatPrice);
            seatTypeRepository.save(seatType);
            vip = seatTypeRepository.findBySeatType(AllStatusStringDTO.vipSeatStatus);
        }
        for (Seat seat: seatList){
            if(seat.getSeatType().getSeatType().equals(AllStatusStringDTO.normalSeatStatus)){
                seat.setSeatType(vip.get());
                seatRepository.save(seat);
            }else if(seat.getSeatType().getSeatType().equals(AllStatusStringDTO.vipSeatStatus)){
                seat.setSeatType(normal.get());
                seatRepository.save(seat);
            }
        }
        return "success";
    }
}
