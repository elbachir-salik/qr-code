package com.bcp.qrcode.controllers;


import com.bcp.qrcode.services.QRCodeService;
import com.bcp.qrcode.services.RibService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final RibService ribService;
    private final QRCodeService qrCodeService;
    public QRCodeController(QRCodeService qrCodeService,RibService ribService) {
        this.ribService = ribService;
        this.qrCodeService = qrCodeService;
    }


    @GetMapping("/generate/{userId}")
    public ResponseEntity<String> generateQRCodeForUser(@PathVariable Long userId,
                                                        @RequestParam(defaultValue = "250") int width,
                                                        @RequestParam(defaultValue = "250") int height) {
        try {
            // Generate the QR code as a Base64 string
            String qrCode = qrCodeService.generateQRCodeForUser(userId, width, height);
            return ResponseEntity.ok(qrCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to generate QR Code: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to retrieve JSON data for a user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserRibData(@PathVariable Long userId) {
        return ResponseEntity.ok(ribService.getUserRibData(userId));
    }
}
