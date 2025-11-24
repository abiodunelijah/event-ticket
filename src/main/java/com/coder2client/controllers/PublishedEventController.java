package com.coder2client.controllers;

import com.coder2client.dtos.GetPublishedEventDetailResponseDto;
import com.coder2client.dtos.ListPublishedEventResponseDto;
import com.coder2client.entity.Event;
import com.coder2client.mappers.EventMapper;
import com.coder2client.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;



    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable) {

        Page<Event> events;

        if (q != null && !q.trim().isEmpty()){
            events = eventService.searchPublishedEvents(q, pageable);
        }else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(events
                .map(eventMapper::toListPublishedEventResponseDto));

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventDetailResponseDto> getPublishedEventDetails(@PathVariable UUID eventId) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailResponseDto)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
