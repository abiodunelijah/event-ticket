package com.coder2client.mappers;


import com.coder2client.dtos.*;
import com.coder2client.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto createTicketTypeRequestDto);

    CreateEventRequest fromDto(CreateEventRequestDto createEventRequestDto);

    CreateEventResponseDto toDto(Event event);
}
