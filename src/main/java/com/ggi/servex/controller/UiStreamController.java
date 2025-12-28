package com.ggi.servex.controller;

import com.ggi.servex.service.OrderViewService;
import com.ggi.servex.service.TableService;
import com.ggi.servex.service.UiEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@CrossOrigin
@RestController
@RequestMapping("/ui/stream")
@RequiredArgsConstructor
public class UiStreamController {

    private final UiEventService uiEventService;
    private final TableService tableService;
    private final OrderViewService orderViewService;

    @GetMapping("/tables")
    public SseEmitter tables() {
        SseEmitter emitter = uiEventService.registerTableEmitter();
        uiEventService.sendOnce(emitter, "tables", tableService.getAll());
        return emitter;
    }

    @GetMapping("/orders")
    public SseEmitter orders() {
        SseEmitter emitter = uiEventService.registerOrderEmitter();
        uiEventService.sendOnce(emitter, "orders", orderViewService.getAll());
        return emitter;
    }
}
