package com.ggi.servex.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class UiEventService {

    private static final Logger log = LoggerFactory.getLogger(UiEventService.class);
    private static final long SSE_TIMEOUT = Duration.ofMinutes(30).toMillis();

    private final List<SseEmitter> tableEmitters = new CopyOnWriteArrayList<>();
    private final List<SseEmitter> orderEmitters = new CopyOnWriteArrayList<>();

    public SseEmitter registerTableEmitter() {
        return registerEmitter(tableEmitters);
    }

    public SseEmitter registerOrderEmitter() {
        return registerEmitter(orderEmitters);
    }

    public void sendTables(Object payload) {
        broadcast(tableEmitters, "tables", payload);
    }

    public void sendOrders(Object payload) {
        broadcast(orderEmitters, "orders", payload);
    }

    public void sendOnce(SseEmitter emitter, String event, Object payload) {
        try {
            emitter.send(SseEmitter.event()
                    .name(event)
                    .data(payload));
        } catch (IOException ex) {
            log.debug("Emitter {} failed while sending {}", emitter, event, ex);
            emitter.completeWithError(ex);
        }
    }

    private SseEmitter registerEmitter(List<SseEmitter> emitters) {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(error -> emitters.remove(emitter));
        return emitter;
    }

    private void broadcast(List<SseEmitter> emitters, String event, Object payload) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(event)
                        .data(payload));
            } catch (IOException ex) {
                log.debug("Emitter {} removed after {} broadcast", emitter, event, ex);
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
