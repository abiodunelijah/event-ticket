package com.coder2client.dtos;

import com.coder2client.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDto {

    private UUID ticketTypeId;
    private TicketStatusEnum status;
    private ListTicketResponseTicketTypeDto ticketType;
}
