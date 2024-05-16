package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;

import javax.swing.*;
import java.awt.*;

public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel pdf;    //Lienzo2D
    String namepdf;

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

        this.setClosable(false);
        this.setIconifiable(false);
        this.setMaximizable(false);
        this.setResizable(false);
        this.setForeground(Color.WHITE);

        bd.add(pdf);
        this.getContentPane().add(bd, BorderLayout.CENTER);

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
