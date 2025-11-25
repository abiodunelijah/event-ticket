package com.coder2client.mappers;


import com.coder2client.dtos.TicketValidationResponseDto;
import com.coder2client.entity.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDto  toTicketValidationResponseDto(TicketValidation ticketValidation);
}
