package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;

import javax.swing.*;
import java.awt.*;

public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel pdf;    //Lienzo2D
    public PDFInternalFrame(){}
    public PDFInternalFrame(String npdf){
        super(npdf,true,true,true,true);
        initComponentes();
    }

    private void initComponentes() {
        bd = new JScrollPane();
        pdf = new ViewPDFPanel();

        this.setClosable(true);
        this.setIconifiable(true);
        this.setMaximizable(true);
        this.setResizable(true);

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
