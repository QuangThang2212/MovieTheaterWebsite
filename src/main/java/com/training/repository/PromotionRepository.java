package com.training.repository;

import com.training.entities.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    public Long countByPromotionId(Integer id);

    Page<Promotion> findByTitleContaining(String search, Pageable pageable);
}
