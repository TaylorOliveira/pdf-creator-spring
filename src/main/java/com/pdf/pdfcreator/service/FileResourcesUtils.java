package com.pdf.pdfcreator.service;

import org.apache.camel.model.Model;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileResourcesUtils {

  public String getStringFileFromResource(String fileName) throws IOException {
    InputStream inputStream = Model.class.getClassLoader()
        .getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new NullPointerException("file not found! " + fileName);
    } else {
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
  }
}
