package com.bcp.qrcode.controllers;

import com.bcp.qrcode.services.PDFService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificate")
public class CertificatePDFController {

    private final PDFService pdfService;

    public CertificatePDFController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> getRibCertificate(@PathVariable Long userId) {
        byte[] pdfContent = pdfService.generateRibCertificate(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "RIB_Certificate_" + userId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}

