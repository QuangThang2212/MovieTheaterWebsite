package com.training.service.impl;

import com.training.dto.AllStatusStringDTO;
import com.training.dto.CinemaRoomDTO;
import com.training.entities.CinemaRoom;
import com.training.entities.Seat;
import com.training.entities.SeatType;
import com.training.repository.CinemaRoomRepository;
import com.training.repository.SeatRepository;
import com.training.repository.SeatTypeRepository;
import com.training.service.CinemaRoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaRoomServiceImpl implements CinemaRoomService {

    @Autowired
    private CinemaRoomRepository cinemaRoomRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    public List<CinemaRoom> listAll() {
        return (List<CinemaRoom>) cinemaRoomRepository.findAll();
    }

    public CinemaRoom save(CinemaRoomDTO cinemaroomDTO) {
        Character [] characters = {'A','B','C','D','E','F'};
        ModelMapper modelMapper = new ModelMapper();
        CinemaRoom cinemaroom = modelMapper.map(cinemaroomDTO, CinemaRoom.class);
        cinemaRoomRepository.save(cinemaroom);
        Optional<SeatType> seatType = seatTypeRepository.findBySeatType(AllStatusStringDTO.normalSeatStatus);
        if(!seatType.isPresent()){
            SeatType seatType1 = new SeatType();
            seatType1.setPrice(AllStatusStringDTO.normalSeatPrice);
            seatType1.setSeatType(AllStatusStringDTO.normalSeatStatus);
            seatTypeRepository.save(seatType1);
            seatType = seatTypeRepository.findBySeatType(AllStatusStringDTO.normalSeatStatus);
        }

        Seat seat;
        for(int i=1;i<=cinemaroom.getSeatQuantity()/6;i++){
            for(int j=0;j<characters.length;j++){
                seat = new Seat();
                seat.setSeatRow(Integer.toString(i));
                seat.setSeatColumn(Character.toString(characters[j]));
                seat.setCinemaRoom(cinemaroom);
                seat.setSeatType(seatType.get());
                seatRepository.save(seat);
            }
        }
        return cinemaroom;
    }

    @Override
    public CinemaRoom findByCinemaRoomName(String name) {
        Optional<CinemaRoom> cinemaRoom = cinemaRoomRepository.findByCinemaRoomName(name);
        return cinemaRoom.orElse(null);
    }

    public CinemaRoom get(Integer id) {
        Optional<CinemaRoom> result = cinemaRoomRepository.findById(id);
        return result.orElse(null);
    }

    public void delete(Integer id) throws Exception {
        Long count = cinemaRoomRepository.countByRoomID(id);
        if (count == null || count == 0) {
            throw new Exception("Could not find any Cinema Room with id: " + id) {
            };
        }
        cinemaRoomRepository.deleteById(id);
    }

    @Override
    public Page<CinemaRoom> findAll(Pageable pageable) {
        return cinemaRoomRepository.findAll(pageable);
    }

    @Override
    public Page<CinemaRoom> findByCinemaRoomNameContaining(String search, Pageable pageable) {
        return cinemaRoomRepository.findByCinemaRoomNameContaining(search, pageable);
    }


}
