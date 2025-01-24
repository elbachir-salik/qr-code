package com.bcp.qrcode.services.impl;

import com.bcp.qrcode.entities.Rib;
import com.bcp.qrcode.repo.RibRepository;
import com.bcp.qrcode.services.PDFService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFServiceImpl implements PDFService {

    private final RibRepository ribRepository;

    public PDFServiceImpl(RibRepository ribRepository) {
        this.ribRepository = ribRepository;
    }

    @Override
    public byte[] generateRibCertificate(Long userId) {
        // Fetch the RIB data from the database
        Rib rib = ribRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No RIB found for user with ID: " + userId));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Set up the PDF writer and document
            PdfWriter pdfWriter = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            // Add content to the PDF
            document.add(new Paragraph("RELEVE D’IDENTITE BANCAIRE"));
            document.add(new Paragraph("Mr/Ms " + rib.getUser().getUsername()));
            document.add(new Paragraph("Code Banque: " + rib.getCodeBanque()));
            document.add(new Paragraph("Code Localité: " + rib.getCodeLocalite()));
            document.add(new Paragraph("N° de Compte: " + rib.getNumeroCompte()));
            document.add(new Paragraph("Clé R.I.B: " + rib.getCleRib()));
            document.add(new Paragraph("Domiciliation: " + rib.getDomiciliation()));
            document.add(new Paragraph("Code SWIFT: " + rib.getCodeSwift()));
            document.add(new Paragraph("RIB Number: " + rib.getRibNumber()));
            document.add(new Paragraph("Date: " + java.time.LocalDate.now()));

            // Close the document
            document.close();

            // Return the PDF content as a byte array
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate RIB certificate PDF", e);
        }
    }
}
