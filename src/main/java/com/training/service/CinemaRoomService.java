package com.training.service;
import com.training.dto.CinemaRoomDTO;
import com.training.entities.CinemaRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CinemaRoomService {
    CinemaRoom save(CinemaRoomDTO cinemaRoomDTO);
    CinemaRoom findByCinemaRoomName(String name);

    List<CinemaRoom> listAll();

    CinemaRoom get(Integer roomID);

    void delete(Integer roomID) throws Exception;

    Page<CinemaRoom> findAll(Pageable pageable);

    Page<CinemaRoom> findByCinemaRoomNameContaining(String search, Pageable pageable);
}
