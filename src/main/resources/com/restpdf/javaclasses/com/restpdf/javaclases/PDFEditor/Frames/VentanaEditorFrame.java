package com.restpdf.javaclases.PDFEditor.Frames;


import com.restpdf.javaclases.PDFEditor.Handlers.PDFWindowHandler;
import com.restpdf.javaclases.PDFEditor.Listeners.PDFEvent;
import com.restpdf.javaclases.PDFEditor.Listeners.ViewPDFListeners;
import com.restpdf.javaclases.PDFEditor.Panels.BackgroundPDFPanel;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VentanaEditorFrame extends JFrame {    //ventanaPrincipal
    JPanel PanelNuevosCampos;
    JPanel PanelOpciones;
    JPanel PanelPDF;
    JButton BotonGuardarCampos;
    JPanel MainInternalFrame;
    PDFWindowHandler PDFWHandler;
    PDFViewHandler PDFVHandler;
    JDesktopPane zonaEscritorio;
    private String nombrepdf;
    BackgroundPDFPanel bg;

    public VentanaEditorFrame(){
        nombrepdf = null;
        initSwingComponents();
        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();
        PDFVHandler = new PDFViewHandler();
    }
    public VentanaEditorFrame(String pdfname){
        nombrepdf = pdfname;

        initSwingComponents();
        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();
        PDFVHandler = new PDFViewHandler();

        PDFInternalFrame pdf_if = new PDFInternalFrame(nombrepdf);
        //aqui el background, separa las 2 cosas
        pdf_if.addInternalFrameListener(PDFWHandler);
        pdf_if.getPdf().addEventListener(PDFVHandler);

        PanelPDF.add(pdf_if);
        pdf_if.setSize(800, 1000);
        pdf_if.setVisible(true);
    }
    private void initSwingComponents() {
        MainInternalFrame = new JPanel();
        MainInternalFrame.setLayout(new BorderLayout());
//--------------------------------------------------------------------------
        PanelOpciones = new JPanel();

        PanelNuevosCampos = new JPanel();
        //añadir lógica de añadir campos
        PanelOpciones.add(PanelNuevosCampos,BorderLayout.NORTH);

        BotonGuardarCampos = new JButton();
        BotonGuardarCampos.setText("Guardar");
        BotonGuardarCampos.setSize(50,50);
        //pòner función de guardar
        PanelOpciones.add(BotonGuardarCampos,BorderLayout.SOUTH);
        PanelOpciones.setSize(50,50);
//--------------------------------------------------------------------------
        PanelPDF = new JPanel();
        PanelPDF.setBorder(BorderFactory.createLineBorder(Color.gray));
        PanelPDF.setForeground(Color.lightGray);
        PanelPDF.setLayout(new BorderLayout());
        PanelPDF.setSize(600,1000);

        zonaEscritorio = new JDesktopPane(); //aqui es donde añadimos los internalframes
        zonaEscritorio.setBackground(Color.BLACK);
        zonaEscritorio.setPreferredSize(new Dimension(600, 1000));
        PanelPDF.add(zonaEscritorio);
//--------------------------------------------------------------------------
        MainInternalFrame.add(PanelOpciones, BorderLayout.WEST);
        MainInternalFrame.add(PanelPDF);

        this.add(MainInternalFrame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
    }

    private class PDFViewHandler implements ViewPDFListeners {   //manejadorLienzo

        @Override
        public void NewFieldCreated(PDFEvent evt) {
            ViewPDFPanel view = ((PDFInternalFrame) zonaEscritorio.getSelectedFrame()).getPdf();
            view.EnableDeleteListener();
        }
    }
}
