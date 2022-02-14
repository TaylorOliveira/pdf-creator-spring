package com.pdf.pdfcreator.controller;

import com.pdf.pdfcreator.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfCreatorController {

    private final PdfService pdfService;

    public PdfCreatorController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/base-64")
    public ResponseEntity<String> pdfCreator() throws IOException {
        return ResponseEntity.ok(pdfService.getBase64Pdf());
    }
}
