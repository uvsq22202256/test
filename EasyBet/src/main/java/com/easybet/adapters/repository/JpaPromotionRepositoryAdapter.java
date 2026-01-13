package com.easybet.adapters.repository;

import com.easybet.domain.entity.Promotion;
import com.easybet.repository.PromotionRepository;
import com.easybet.repository.jpa.SpringDataPromotionRepository;
import com.easybet.infrastructure.persistence.mapper.PromotionMapper;
import com.easybet.infrastructure.persistence.entity.PromotionEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaPromotionRepositoryAdapter implements PromotionRepository {

    private final SpringDataPromotionRepository springDataRepository;
    private final PromotionMapper mapper;

    public JpaPromotionRepositoryAdapter(SpringDataPromotionRepository springDataRepository, PromotionMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Promotion save(Promotion promotion) {
        PromotionEntity entity = mapper.toEntity(promotion);
        PromotionEntity savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return springDataRepository.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<Promotion> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}