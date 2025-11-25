package com.coder2client.dtos;

import com.coder2client.enums.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDto {

    private UUID ticketId;
    private TicketValidationStatusEnum status;

}
