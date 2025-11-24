package com.coder2client.services;

import com.coder2client.entity.QrCode;
import com.coder2client.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket  ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
