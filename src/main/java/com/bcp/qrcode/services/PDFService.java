package com.bcp.qrcode.services;

import java.io.IOException;

public interface PDFService {
    byte[] generateRibCertificate(Long userId) throws IOException;
}
