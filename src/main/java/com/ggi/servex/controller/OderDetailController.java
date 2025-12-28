package com.ggi.servex.controller;

import com.ggi.servex.dto.OderDetailDto;
import com.ggi.servex.service.OderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/order-details")
@RequiredArgsConstructor
public class OderDetailController {

    private final OderDetailService oderDetailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OderDetailDto create(@RequestBody OderDetailDto detailDto) {
        return oderDetailService.create(detailDto);
    }

    @GetMapping("/{id}")
    public OderDetailDto get(@PathVariable("id") String detailId) {
        return oderDetailService.getById(detailId);
    }

    @GetMapping
    public List<OderDetailDto> getAll() {
        return oderDetailService.getAll();
    }

    @PutMapping("/{id}")
    public OderDetailDto update(@PathVariable("id") String detailId, @RequestBody OderDetailDto detailDto) {
        return oderDetailService.update(detailId, detailDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String detailId) {
        oderDetailService.delete(detailId);
    }
}
