package com.restpdf.javaclases.oldPDFmanager.PDFStructure;

import com.itextpdf.kernel.pdf.PdfReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PDFPageHandler {
    private PdfReader reader;
    private StringBuilder text;
    private ArrayList<ImgPDF> images;



    public PDFPageHandler(){
        reader = null;
        text = null;
        images = new ArrayList<>();
    }

    public PDFPageHandler(String n){
        try {
            reader = new PdfReader(n);
            text = new StringBuilder();
            images = new ArrayList<>();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PDFPageHandler(PdfReader r) {
        reader = r;
        text = null;
        images = new ArrayList<>();
    }

    public void addImg(String url) {
        ImgPDF newI = new ImgPDF(url,null,0,0);
        images.add(newI);
    }

    public void addImg(String url, int x, int y) {
        ImgPDF newI = new ImgPDF(url,null,x,y);
        images.add(newI);
    }

    public void addText(String str) {
        text.append(str);
    }

    public void addImg(BufferedImage bi) {
       ImgPDF newI = new ImgPDF("",bi,0,0);
       images.add(newI);
    }

    public PdfReader getReader() {
        return reader;
    }

    public StringBuilder getText() {
        return text;
    }

    public ArrayList<ImgPDF> getImages() {
        return images;
    }
}
