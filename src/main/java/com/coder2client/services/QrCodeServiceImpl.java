package com.coder2client.services;

import com.coder2client.entity.QrCode;
import com.coder2client.entity.Ticket;
import com.coder2client.enums.QrCodeStatusEnum;
import com.coder2client.exceptions.QrCodeGenerationException;
import com.coder2client.exceptions.QrCodeNotFoundException;
import com.coder2client.repositories.QrCodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try {
            UUID uniqueUUID = UUID.randomUUID();

            String qrCodeImage = generateQrCodeImage(uniqueUUID);

            QrCode qrCode = new QrCode();
            qrCode.setId(uniqueUUID);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);

            return qrCodeRepository.saveAndFlush(qrCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new QrCodeGenerationException("Failed to generate QR code.", e);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId).orElseThrow(QrCodeNotFoundException::new);

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        }catch (IllegalArgumentException illegalArgumentException){
            log.error("Invalid base64 QR Code for ticket ID:{}", ticketId, illegalArgumentException);
            throw new QrCodeNotFoundException();
        }

    }

    private String generateQrCodeImage(UUID uniqueUUID) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueUUID.toString(),
                BarcodeFormat.QR_CODE,
                QR_HEIGHT,
                QR_WIDTH
        );

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}



























