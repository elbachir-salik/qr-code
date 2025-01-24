package com.bcp.qrcode.controllers;

import com.bcp.qrcode.services.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> generateQRCodeForUser(@PathVariable Long userId) throws IOException {
        // Generate QR code that links to the certificate URL
        String base64QRCode = qrCodeService.generateQRCodeForUser(userId, 250, 250);

        return ResponseEntity.ok(base64QRCode);
    }
}
