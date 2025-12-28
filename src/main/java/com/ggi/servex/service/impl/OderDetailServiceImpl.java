package com.ggi.servex.service.impl;

import tools.jackson.databind.ObjectMapper;
import com.ggi.servex.dto.OderDetailDto;
import com.ggi.servex.entity.OderDetailEntity;
import com.ggi.servex.repository.OderDetailRepository;
import com.ggi.servex.service.OderDetailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OderDetailServiceImpl implements OderDetailService {

    private final OderDetailRepository oderDetailRepository;
    private final ObjectMapper objectMapper;

    @Override
    public OderDetailDto create(OderDetailDto detail) {
            OderDetailEntity entity = toEntity(detail);
            entity.setOderDetailId(null);
            return toDto(oderDetailRepository.save(entity));
    }

    @Override
    public OderDetailDto update(String oderDetailId, OderDetailDto detail) {
        OderDetailEntity existing = getEntity(oderDetailId);
        OderDetailEntity toSave = toEntity(detail);
        toSave.setOderDetailId(existing.getOderDetailId());
        return toDto(oderDetailRepository.save(toSave));
    }

    @Override
    public OderDetailDto getById(String oderDetailId) {
        return toDto(getEntity(oderDetailId));
    }

    @Override
    public List<OderDetailDto> getAll() {
        return oderDetailRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String oderDetailId) {
        oderDetailRepository.delete(getEntity(oderDetailId));
    }

    private OderDetailEntity getEntity(String oderDetailId) {
        return oderDetailRepository.findById(oderDetailId)
                .orElseThrow(() -> new EntityNotFoundException("Order detail not found: " + oderDetailId));
    }

    private OderDetailDto toDto(OderDetailEntity entity) {
        return objectMapper.convertValue(entity, OderDetailDto.class);
    }

    private OderDetailEntity toEntity(OderDetailDto dto) {
        return objectMapper.convertValue(dto, OderDetailEntity.class);
    }
}
