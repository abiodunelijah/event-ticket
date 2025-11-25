package com.coder2client.repositories;

import com.coder2client.entity.QrCode;
import com.coder2client.enums.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);
    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}
