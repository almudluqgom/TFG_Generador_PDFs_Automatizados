package com.restpdf.javaclases.PDFEditor.Frames;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.geom.*;
import com.itextpdf.kernel.pdf.canvas.*;
import com.itextpdf.kernel.pdf.xobject.*;
import com.itextpdf.layout.element.Image;

import com.restpdf.javaclases.PDFEditor.Panels.BackgroundPDFPanel;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.bdclases.BDForms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel Panelpdf;    //Lienzo2D
    String namepdf, namenewpdf;
    BackgroundPDFPanel bg;


    public PDFInternalFrame(String npdf) {
        super(npdf, true, false, false, false);
        namepdf = npdf;
        namenewpdf =  npdf.replace(".pdf", "_new.pdf");

        initComponentes();
    }
    private void initComponentes() {

        bd = new JScrollPane();
        Panelpdf = new ViewPDFPanel();

        try {
            bg = new BackgroundPDFPanel(namepdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        java.awt.Rectangle crop = new java.awt.Rectangle(0, 0, 21, 29);
        bd.setBounds(crop);
        add(bd);

        this.setClosable(false);
        this.setResizable(false);

        this.setForeground(Color.WHITE);

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

}
