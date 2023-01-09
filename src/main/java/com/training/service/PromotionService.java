package com.training.service;

import com.training.dto.PromotionDTO;
import com.training.entities.Promotion;
import com.training.util.PromotionNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {

    Promotion save(PromotionDTO promotionDTO);

    List<Promotion> listAll();

    Promotion get(Integer promotionId) throws PromotionNotFoundException;

    void delete(Integer promotionId) throws PromotionNotFoundException;

    Page<Promotion> findAll(Pageable pageable);

    Page<Promotion> findByTitle(String search, Pageable pageable);
}
