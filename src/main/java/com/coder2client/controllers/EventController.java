package com.coder2client.controllers;

import com.coder2client.dtos.CreateEventRequest;
import com.coder2client.dtos.CreateEventRequestDto;
import com.coder2client.dtos.CreateEventResponseDto;
import com.coder2client.entity.Event;
import com.coder2client.mappers.EventMapper;
import com.coder2client.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {

        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());
        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createdEventResponseDto = eventMapper.toDto(createdEvent);

        return ResponseEntity.ok(createdEventResponseDto);
    }
}
