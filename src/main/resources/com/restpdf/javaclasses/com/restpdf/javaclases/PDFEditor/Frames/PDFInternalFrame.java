package com.restpdf.javaclases.PDFEditor.Frames;

import com.itextpdf.kernel.pdf.*;

import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.restpdf.javaclases.PDFEditor.Panels.BackgroundPDFPanel;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;
import com.restpdf.javaclases.PDFEditor.Tools.PageComponent;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.bdclases.BDForms;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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

        //never forget close the docs
        origPdf.close();
        if (pdf.getNumberOfPages() != 0)
            pdf.close();
        else{
            //pdf.open();
            pdf.addNewPage(); // << avoids empty doc
            pdf.close();
        }
        delete();

    }
    private void initComponentes() {

        bd = new JScrollPane();
        Panelpdf = new ViewPDFPanel();

//        try {
//            bg = new BackgroundPDFPanel(namepdf);
//            bd.add(bg);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //initalization
        createPages();
        PdfPage firstp = obtainFirstPage();

        //test display 1st page
        //PageComponent page = new PageComponent("C:\\\\Users\\\\Almuchuela\\\\Downloads\\\\pagina4.jpeg",firstp);
        PageComponent page = auxp;
        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(page.getW(), page.getH()));

        JLabel picLabel = new JLabel(new ImageIcon(page.getBi()));
        panel.add(picLabel);
        //bd.add(page);
        bd = new JScrollPane(panel);

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

        auxp = pages.get(0);
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
    public void delete(){
        File myObj = new File(namenewpdf);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

}
