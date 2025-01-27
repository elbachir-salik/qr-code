package com.bcp.qrcode.services.impl;

import com.bcp.qrcode.entities.Rib;
import com.bcp.qrcode.repo.RibRepository;
import com.bcp.qrcode.services.PDFService;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class PDFServiceImpl implements PDFService {

    private final RibRepository ribRepository;

    public PDFServiceImpl(RibRepository ribRepository) {
        this.ribRepository = ribRepository;
    }

    @Override
    public byte[] generateRibCertificate(Long userId) throws IOException {
        // Fetch the RIB and user data
        Rib rib = ribRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No RIB found for user with ID: " + userId));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Load Arabic font
        String fontPath = "src/main/resources/fonts/Amiri-Regular.ttf"; // Update with the actual path to your font
        PdfFont arabicFont = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);

        // Add title
        Paragraph title = new Paragraph("RELEVE D’IDENTITE BANCAIRE")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16);
        document.add(title);

        // Add Arabic and French description
        String arabicText = "وﺃ ﻢﻜﻴﻨﺋﺍﺪﻟ هﻮﻣﺪﻘﺘﻟ, ﻢﻜﺑ صﺎﺨﻟﺍ ﻒﺸﻜﻟﺍ اﺬﻫ دﺍﺪﻋﺇ ﻢﺗ, ﻞﻈﻓﺃ ﻞﻜﺸﺑ ﻢﻜﺘﻣﺪِﺧ ﻞﺟﺃ ﻦﻣ\n" +
                ". ﺔﺼﻟﺎﺨﻤﻟﺍ ءاﺩﺃ وﺍ, تﻼﻳﻮﺤﺘﻟﺎﻛﻢﻜﺑﺎﺴﺣ ﻰﻠﻋ تﺎﻴﻠﻤﻌﻠﻻ ﻞﻴﺠﺴﺘﺑ ﻦﻴﺒﻟﺎﻄﻤﻟﺍ ﻢﻜﻴﻨﻳﺪﻣ\n" +
                "ﻢﻜﺒﻨﺠﻳﻭ ﺎﻬﺑ ﻢﺘﻤﻗ ﻲﺘﻟﺍ تﺎﻴﻠﻤﻌﻠﻟ ﻞﻀﻓﺃ ﻼﻴﺠﺴﺗ ﻢﻜﻟ ﻦﻤﻀﻳ ﻒﺸﻜﻟﺍ اﺬﻫ لﺎﻤﻌﺘﺳﺍ نﺇ\n" +
                "جﺍﺭﺩﻹﺍ ﻲﻓ ﺮﻴﺧﺄﺘﻟﺍ وﺃ ﺄﻄﺨﻟﺍ ﻦﻋ ﺔﺠﺗﺎﻨﻟﺍ تﺎﻳﺎﻜﺸﻟﺍ";
        String frenchText = "Pour plus de commodité, nous avons établi, pour vous, ce relevé, à remettre à vos créanciers ou " +
                "débiteurs appelés à faire inscrire des opérations à votre compte (virements, paiement de quittance, etc.)\n" +
                "Son utilisation vous garantit le bon enregistrement des opérations en cause et vous évite des réclamations pour erreur ou retards d’imputation.";

        // Add Arabic text with right alignment
        Paragraph arabicParagraph = new Paragraph(arabicText)
                .setFont(arabicFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(arabicParagraph);

        // Add French text with left alignment
        Paragraph frenchParagraph = new Paragraph(frenchText)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(frenchParagraph);

        // Add table
        Table table = new Table(2);
        table.setWidth(UnitValue.createPercentValue(100));
//        table.addHeaderCell(new Cell().add(new Paragraph("Champ").setBold()));
//        table.addHeaderCell(new Cell().add(new Paragraph("Valeur").setBold()));
        table.addCell(new Cell().add(new Paragraph("Code Banque")));
        table.addCell(new Cell().add(new Paragraph(rib.getCodeBanque())));
        table.addCell(new Cell().add(new Paragraph("Code Localité")));
        table.addCell(new Cell().add(new Paragraph(rib.getCodeLocalite())));
        table.addCell(new Cell().add(new Paragraph("N° de Compte")));
        table.addCell(new Cell().add(new Paragraph(rib.getNumeroCompte())));
        table.addCell(new Cell().add(new Paragraph("Clé R.I.B")));
        table.addCell(new Cell().add(new Paragraph(rib.getCleRib())));
        table.addCell(new Cell().add(new Paragraph("Domiciliation")));
        table.addCell(new Cell().add(new Paragraph(rib.getDomiciliation())));
        table.addCell(new Cell().add(new Paragraph("Code SWIFT")));
        table.addCell(new Cell().add(new Paragraph(rib.getCodeSwift())));
        table.addCell(new Cell().add(new Paragraph("RIB Number")));
        table.addCell(new Cell().add(new Paragraph(rib.getRibNumber())));
        document.add(table);

        document.close();
        return outputStream.toByteArray();
    }

}
