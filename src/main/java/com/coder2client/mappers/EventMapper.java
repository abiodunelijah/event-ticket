package com.coder2client.mappers;


import com.coder2client.dtos.*;
import com.coder2client.entity.Event;
import com.coder2client.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto createTicketTypeRequestDto);

    CreateEventRequest fromDto(CreateEventRequestDto createEventRequestDto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsTicketTypeResponseDto toGetEventDetailsTicketTypeResponseDto(TicketType ticketType);

    GetEventDetailResponseDto toGetEventDetailResponseDto(Event event);

}
