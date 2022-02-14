package com.pdf.pdfcreator.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PdfServiceImpl implements PdfService {

    private static final String ORIG = "static/index.html";

    @Override
    public String getBase64Pdf() throws IOException {

        String html = getHTMLString();

        byte[] encoded = getBytesPDF(html);

        return new String(encoded);
    }

    private String getHTMLString() throws IOException {
        FileResourcesUtils app = new FileResourcesUtils();

        return app.getStringFileFromResource(ORIG);
    }

    private byte[] getBytesPDF(String html) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));

        pdfDocument.setDefaultPageSize(new PageSize(320, 872));

        HtmlConverter.convertToPdf(html, pdfDocument, getConverterProperties());

        byte[] bytes = outputStream.toByteArray();
        return Base64.getEncoder().encode(bytes);
    }

    private ConverterProperties getConverterProperties() {
        ConverterProperties converterProperties = new ConverterProperties();

        FontProvider fontProvider = new DefaultFontProvider(false,
                false, false);
        fontProvider.addStandardPdfFonts();
        fontProvider.addFont(StandardFonts.HELVETICA);

        return converterProperties.setFontProvider(fontProvider);
    }
}
