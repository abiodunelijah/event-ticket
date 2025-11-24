package com.coder2client.mappers;

import com.coder2client.dtos.GetTicketResponseDto;
import com.coder2client.dtos.ListTicketResponseDto;
import com.coder2client.dtos.ListTicketResponseTicketTypeDto;
import com.coder2client.entity.Ticket;
import com.coder2client.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketResponseTicketTypeDto toListTicketResponseTicketTypeDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.eventName")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.eventVenue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.eventStart")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.eventEnd")
    GetTicketResponseDto  toGetTicketResponseDto(Ticket ticket);
}
