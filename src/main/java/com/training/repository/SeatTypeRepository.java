package com.training.repository;

import com.training.entities.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {
    Optional<SeatType> findBySeatType(String seatType);
}
