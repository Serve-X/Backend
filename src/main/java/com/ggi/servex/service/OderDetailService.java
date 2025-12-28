package com.ggi.servex.service;

import com.ggi.servex.dto.OderDetailDto;

import java.util.List;

public interface OderDetailService {
    OderDetailDto create(OderDetailDto detail);

    OderDetailDto update(String oderDetailId, OderDetailDto detail);

    OderDetailDto getById(String oderDetailId);

    List<OderDetailDto> getAll();

    void delete(String oderDetailId);
}
