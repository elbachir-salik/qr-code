package com.bcp.qrcode.controllers;

import com.bcp.qrcode.services.PDFService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/certificate")
public class CertificatePDFController {

    private final PDFService pdfService;

    public CertificatePDFController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> generateCertificate(@PathVariable Long userId) {
        try {
            byte[] pdfData = pdfService.generateRibCertificate(userId);

            // Set headers for the PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=RIB_Certificate_" + userId + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating certificate: " + e.getMessage());
        }
    }
}

