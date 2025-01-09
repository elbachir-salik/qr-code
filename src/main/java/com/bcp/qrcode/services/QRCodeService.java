package com.bcp.qrcode.services;


import java.io.IOException;

public interface QRCodeService {
    String generateQRCodeForUser(Long userId, int width, int height) throws IOException;
}
