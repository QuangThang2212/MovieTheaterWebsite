package com.training.repository;

import com.training.entities.CinemaRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Integer> {

    public Long countByRoomID(Integer id);

    Page<CinemaRoom> findByCinemaRoomNameContaining(String search, Pageable pageable);

    CinemaRoom findByRoomID(int roomID);

    Optional<CinemaRoom> findByCinemaRoomName(String name);
}
