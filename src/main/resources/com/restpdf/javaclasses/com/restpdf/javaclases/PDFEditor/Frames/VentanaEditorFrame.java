package com.restpdf.javaclases.PDFEditor.Frames;


import com.restpdf.javaclases.PDFBuilder.*;
import com.restpdf.javaclases.PDFEditor.Handlers.PDFWindowHandler;
import com.restpdf.javaclases.PDFEditor.Listeners.PDFEvent;
import com.restpdf.javaclases.PDFEditor.Listeners.ViewPDFListeners;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;

public class VentanaEditorFrame extends JFrame {    //ventanaPrincipal
    JPanel PanelNuevosCampos,PanelOpciones, PanelPDF,MainInternalFrame;
    JButton BotonGuardarCampos;
    PDFWindowHandler PDFWHandler;
    PDFViewHandler PDFVHandler;
    JDesktopPane zonaEscritorio;
    BufferedImage BiPaginaPDF;
    private String nombrepdf;
        public VentanaEditorFrame(String pdfname){
        nombrepdf = pdfname;
        initSwingComponents();
        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();
        PDFVHandler = new PDFViewHandler();

        PDFInternalFrame pdf_if = new PDFInternalFrame(nombrepdf);

        pdf_if.addInternalFrameListener(PDFWHandler);
        pdf_if.getPanelpdf().addEventListener(PDFVHandler);
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
        BotonGuardarCampos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrintWriter pout = new PrintWriter(System.out);
//                if (argv.length == 0) {
//                    pout = new PrintWriter(System.out);
//                } else {
//                    if (new File(argv[0]).exists()) {
//                        throw new IOException(
//                                "Output file " + argv[0] + " already exists");
//                    }
//                    pout = new PrintWriter(new FileWriter(argv[0]));
//                }
                PDF p = new PDF(pout);
//                Page p1 = new Page(p);
//                p1.add(new MoveTo(p, 100, 600));
//                p1.add(new Text(p, "Hello world, live on the web."));
//                p1.add(new Text(p, "Hello world, line 2 on the web."));
//                p.add(p1);
//                p.setAuthor("Ian F. Darwin");
                p.writePDF();
            }
        });
        PanelOpciones.add(BotonGuardarCampos,BorderLayout.SOUTH);
        PanelOpciones.setSize(50,100);
//--------------------------------------------------------------------------
        PanelPDF = new JPanel();
        PanelPDF.setBorder(BorderFactory.createLineBorder(Color.gray));
        PanelPDF.setForeground(Color.lightGray);
        PanelPDF.setLayout(new BorderLayout());
        PanelPDF.setSize(1000,1000);

        zonaEscritorio = new JDesktopPane();
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
            ViewPDFPanel view = ((PDFInternalFrame) zonaEscritorio.getSelectedFrame()).getPanelpdf();
            view.EnableDeleteListener();
        }
    }
}
