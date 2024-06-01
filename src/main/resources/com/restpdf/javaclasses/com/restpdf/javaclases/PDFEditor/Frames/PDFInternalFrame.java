package com.restpdf.javaclases.PDFEditor.Frames;

import com.itextpdf.kernel.pdf.*;

import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.text.Chunk;
import com.restpdf.javaclases.PDFBuilder.Page;
import com.restpdf.javaclases.PDFEditor.Panels.BackgroundPDFPanel;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;
import com.restpdf.javaclases.PDFEditor.Tools.PageComponent;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.bdclases.BDForms;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel Panelpdf;    //Lienzo2D
    String namepdf, namenewpdf;
    BackgroundPDFPanel bg;
    PageComponent auxp;
    PdfDocument pdf, origPdf;
    ArrayList<PageComponent> pages;

    public PDFInternalFrame(String npdf) {
        super(npdf, true, false, false, false);
        namepdf = npdf;
        namenewpdf =  npdf.replace(".pdf", "_new.pdf");

        try {
            origPdf = new PdfDocument(new PdfReader(namepdf));
            pdf = new PdfDocument(new PdfWriter(namenewpdf));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pages= new ArrayList<>();

        initComponentes();
        origPdf.close();
        if (pdf.getNumberOfPages() != 0)
            pdf.close();
        else{
            //pdf.open();
            pdf.addNewPage(); // << this will do the trick.
            pdf.close();
        }
    }
    private void initComponentes() {

        java.awt.Rectangle crop = new java.awt.Rectangle(0, 0, 21, 29);

        bd = new JScrollPane();
        Panelpdf = new ViewPDFPanel();

        try {
            bg = new BackgroundPDFPanel(namepdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PdfPage firstp = obtainFirstPage();

        //test display 1st page
        PageComponent page = new PageComponent("C:\\\\Users\\\\Almuchuela\\\\Downloads\\\\pagina4.jpeg",firstp);
        bd.add(page);

        //initalization
        createPages();

        bd.setBounds(crop);
        add(bd);

        this.setClosable(false);
        this.setResizable(false);

        this.setForeground(Color.WHITE);
    }

    private PdfPage obtainFirstPage() {
        PdfPage origPage = null;
        try {
            origPdf = new PdfDocument(new PdfReader(namepdf));
            origPage = origPdf.getPage(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return origPage;
    }

    public JScrollPane getBd() {
        return bd;
    }

    public void setBd(JScrollPane bd) {
        this.bd = bd;
    }

    public ViewPDFPanel getPanelpdf() {
        return Panelpdf;
    }

    public void setPanelpdf(ViewPDFPanel panelpdf) {
        this.Panelpdf = panelpdf;
    }

    public void createPages(){

        PdfFormXObject pageCopy;
        PdfPage currentp;
        Image img;
        String newpath;

        BDForms based = new BDForms();
        StringEncoder se = new StringEncoder();

        String absp = based.getCarpeta();
        absp = se.decodeFolder(absp);

        try {
            for(int i=1;i<=origPdf.getNumberOfPages();i++){

                newpath = absp + "\\newFile" + i + ".jpeg";
                currentp = origPdf.getPage(i);
                pageCopy = currentp.copyAsFormXObject(pdf);

                if (pageCopy != null) {
                    img = new Image(pageCopy);
                    auxp = new PageComponent(img, currentp);
                    pages.add(auxp);
                    auxp.SaveImage(newpath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
