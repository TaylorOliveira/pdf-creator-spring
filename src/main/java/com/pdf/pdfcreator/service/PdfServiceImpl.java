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

import javax.swing.text.MaskFormatter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PdfServiceImpl implements PdfService {

    private static final String ORIG = "static/index.html";

    @Override
    public String getBase64Pdf() throws IOException, ParseException {

        String html = getHTMLString();

        html = replaceInfos(html);

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

        pdfDocument.setDefaultPageSize(new PageSize(320, 1240));

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

    private String replaceInfos(String content) throws ParseException {
        content = content.replace("{{paymentId}}", UUID.randomUUID().toString());
        content = content.replace("{{amount}}", "100,00");
        content = content.replace("{{dateTime}}", getCurrentDate());
        content = content.replace("{{paymentMethod}}", "PIX");
        content = content.replace("{{description}}", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...");
        content = content.replace("{{payerName}}", "Taylor Oliveira");
        content = content.replace("{{cpfCnpj}}", formatCpfCnpj("00000000000"));
        content = content.replace("{{bank}}", "BANCO TRIÂNGULO SA.");
        content = content.replace("{{issuer}}", "00001");
        content = content.replace("{{numberAccount}}", "000001");
        content = content.replace("{{accountType}}", "Conta digital");
        content = content.replace("{{currentDate}}", getCurrentDate());
        return content;
    }

    private String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private String formatCpfCnpj(String cpfCnpj) throws ParseException {
        MaskFormatter mask;
        if (cpfCnpj.length() == 11) {
        // CPF
            mask = new MaskFormatter("###.###.###-##");
        } else {
            mask = new MaskFormatter("###.###.###/####-##");
        }
        // CNPJ
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(cpfCnpj);
    }
}
