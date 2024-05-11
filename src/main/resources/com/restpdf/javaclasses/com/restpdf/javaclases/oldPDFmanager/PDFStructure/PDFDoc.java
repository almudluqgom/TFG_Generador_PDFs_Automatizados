package com.restpdf.javaclases.oldPDFmanager.PDFStructure;

import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.FileNotFoundException;

public class PDFDoc {
    private String nombrepdf;
    private PdfReader reader;
    private PdfWriter writer;
    private PdfDocument pdfdoc;
    private Document doc;
    private PDFPageHandler pagehandler;

    public PDFDoc() {
        writer = null;
        pdfdoc = null;
        doc = null;
        nombrepdf = null;
    }

    public PDFDoc(String nom) throws IOException {
        nombrepdf = nom;
        reader = new PdfReader(nombrepdf);
        pdfdoc = new PdfDocument(reader);
        pagehandler = new PDFPageHandler(reader);
    }

    public void ExtraerTexto() {
        int num = pdfdoc.getNumberOfPages();
        for (int i = 1; i <= num; i++) {
            String str = PdfTextExtractor.getTextFromPage(pdfdoc.getPage(i));
            pagehandler.addText(str);
            //intentar extraer la fuente?
        }
    }
    public void createPDFFormFilled() {
        String nfilled = nombrepdf + "_filled";

        try {
            writer = new PdfWriter(nfilled);
            pdfdoc = new PdfDocument(writer);
            pdfdoc.addNewPage();
            //insertar todos los datos

            doc = new Document(pdfdoc);
            doc.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void ExtraerImg(int pag) {

        pdfdoc = new PdfDocument(reader);
        ExtractorIMGPDF imgs = new ExtractorIMGPDF();
        PdfCanvasProcessor pdfCanvasProcessor = new PdfCanvasProcessor(imgs);
        pdfCanvasProcessor.processPageContent(pdfdoc.getPage(pag));
        pdfdoc.close();

        for (BufferedImage bi : imgs.getImages()) {
            pagehandler.addImg(bi);
        }
    }
    public void insertImgs(){

    }
}
