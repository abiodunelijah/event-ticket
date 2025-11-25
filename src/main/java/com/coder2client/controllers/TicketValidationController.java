package com.coder2client.controllers;

import com.coder2client.dtos.TicketValidationRequestDto;
import com.coder2client.dtos.TicketValidationResponseDto;
import com.coder2client.entity.TicketValidation;
import com.coder2client.enums.TicketValidationMethod;
import com.coder2client.mappers.TicketValidationMapper;
import com.coder2client.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket-validations")
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> toTicketValidationResponseDto(@RequestBody TicketValidationRequestDto ticketValidationRequestDto) {
        TicketValidationMethod method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;
        if (TicketValidationMethod.MANUAL.equals(method)) {
             ticketValidation =  ticketValidationService.validateTicketManually(ticketValidationRequestDto.getId());
        }else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(ticketValidationRequestDto.getId());
        }
        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
    }
}
