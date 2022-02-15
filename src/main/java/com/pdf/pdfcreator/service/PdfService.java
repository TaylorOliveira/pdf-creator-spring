package com.pdf.pdfcreator.service;

import java.io.IOException;
import java.text.ParseException;

public interface PdfService {

    String getBase64Pdf() throws IOException, ParseException;
}
