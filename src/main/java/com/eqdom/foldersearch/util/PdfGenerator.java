package com.eqdom.foldersearch.util;

import org.springframework.stereotype.Component;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.FileOutputStream;

@Component
public class PdfGenerator {

    public String generatePdf(String html, String filename)throws Exception{
        String path = "documents/contracts/"+filename;
        FileOutputStream output = new FileOutputStream(path);
        PdfRendererBuilder builder = new PdfRendererBuilder();

        builder.withHtmlContent(html,null).toStream(output).run();

        output.close();

        return path;


    }
}
