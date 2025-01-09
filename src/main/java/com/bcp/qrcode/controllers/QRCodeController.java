package com.bcp.qrcode.controllers;

import com.bcp.qrcode.services.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> generateQRCodeForUser(@PathVariable Long userId,
                                                        @RequestParam(defaultValue = "250") int width,
                                                        @RequestParam(defaultValue = "250") int height) {
        try {
            String qrCode = qrCodeService.generateQRCodeForUser(userId, width, height);
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-store, no-cache, must-revalidate")
                    .body(qrCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to generate QR Code: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
