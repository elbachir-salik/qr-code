package com.bcp.qrcode.services.impl;


import com.bcp.qrcode.entities.Rib;
import com.bcp.qrcode.repo.RibRepository;
import com.bcp.qrcode.services.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final RibRepository ribRepository;

    public QRCodeServiceImpl(RibRepository ribRepository) {
        this.ribRepository = ribRepository;
    }

    @Override
    public String generateQRCodeForUser(Long userId, int width, int height) throws IOException {
        // Fetch the RIB of the user from the database
        Rib rib = ribRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No RIB found for user with ID: " + userId));
        System.out.println("Fetched RIB: " + rib.getRibNumber() + ", Username: " + rib.getUser().getUsername());
        // Create the JSON object to encode in the QR code
        String json = String.format("{\"user_id\": %d, \"username\": \"%s\", \"rib\": \"%s\"}",
                rib.getUser().getId(),
                rib.getUser().getUsername(),
                rib.getRibNumber());
        System.out.println("JSON to encode: " + json); // Debug output

        try {

            System.out.println("Encoding JSON into QR Code: " + json);
            // Generate QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(json, BarcodeFormat.QR_CODE, width, height);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Black or white
                }
            }

            // Convert BufferedImage to Base64 String
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
            byte[] pngData = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        } catch (WriterException e) {
            throw new RuntimeException("Failed to generate QR Code: " + e.getMessage());
        }
    }
}
