package com.training.service.impl;

import com.training.dto.PromotionDTO;
import com.training.entities.Promotion;
import com.training.repository.PromotionRepository;
import com.training.service.PromotionService;
import com.training.util.PromotionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> listAll(){
        return (List<Promotion>) promotionRepository.findAll();
    }

    public Promotion save(PromotionDTO promotionDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Promotion promotion = modelMapper.map(promotionDTO, Promotion.class);
        return promotionRepository.save(promotion);
    }

    public Promotion get(Integer id) throws PromotionNotFoundException {
        Optional<Promotion> result = promotionRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new PromotionNotFoundException("Could not find any Promotion with id: " + id);
    }

    public void delete(Integer id) throws PromotionNotFoundException {
        Long count = promotionRepository.countByPromotionId(id);
        if(count == null || count == 0){
            throw new PromotionNotFoundException("Could not find any Promotion with id: " + id);
        }
        promotionRepository.deleteById(id);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public Page<Promotion> findByTitle(String search, Pageable pageable) {
        return promotionRepository.findByTitleContaining(search, pageable);
    }
}
