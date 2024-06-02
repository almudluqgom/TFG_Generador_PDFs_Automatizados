package com.restpdf.javaclases.PDFEditor.Panels;

import com.itextpdf.kernel.pdf.PdfReader;

import javax.swing.*;
import java.io.IOException;


public class BackgroundPDFPanel extends JTextPane {

    String npdf;
    PdfReader reader;
    StringBuilder textopdf;

    public BackgroundPDFPanel() {
        reader = null;
    }

    public BackgroundPDFPanel(String s) throws IOException {

            npdf = s;
//        reader = new PdfReader(s);
//
//        int pages = reader.getNumberOfPages();
//        textopdf = new StringBuilder();
//
//        for (int i = 1; i <= pages; i++) {
//            textopdf.append(PdfTextExtractor.getTextFromPage(reader, i));
//        }
//
//        StyledDocument doc = this.getStyledDocument();
//
//        Style style = this.addStyle("Color Style", null);
//        StyleConstants.setForeground(style, Color.BLACK);
//        try {
//            doc.insertString(doc.getLength(), textopdf.toString(), style);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }

        this.setEditable(false);


    }

    public String getNpdf() {
        return npdf;
    }

    public void setNpdf(String npdf) {
        this.npdf = npdf;
    }


}
