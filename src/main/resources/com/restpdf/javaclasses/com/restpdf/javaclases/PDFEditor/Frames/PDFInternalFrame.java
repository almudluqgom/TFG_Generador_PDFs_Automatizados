package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Panels.BackgroundPDFPanel;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;
import javafx.scene.layout.Background;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel pdf;    //Lienzo2D
    String namepdf;
    BackgroundPDFPanel bg;

    public PDFInternalFrame(String npdf) {
        super(npdf, true, false, false, false);
        namepdf = npdf;
        initComponentes();
    }

    public PDFInternalFrame() {
        super("Nuevo PDF", true, false, false, false);
        namepdf = "";
        initComponentes();
    }

    private void initComponentes() {
        bd = new JScrollPane();
        pdf = new ViewPDFPanel();
        try {
            bg = new BackgroundPDFPanel(namepdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setClosable(false);
        this.setIconifiable(false);
        this.setMaximizable(false);
        this.setResizable(false);
        this.setForeground(Color.WHITE);

//        bd.add(pdf);
//        this.getContentPane().add(bd, BorderLayout.CENTER);
        bd.add(bg);
        this.getContentPane().add(bg, BorderLayout.CENTER);

    }

    public JScrollPane getBd() {
        return bd;
    }

    public void setBd(JScrollPane bd) {
        this.bd = bd;
    }

    public ViewPDFPanel getPdf() {
        return pdf;
    }

    public void setPdf(ViewPDFPanel pdf) {
        this.pdf = pdf;
    }


}
