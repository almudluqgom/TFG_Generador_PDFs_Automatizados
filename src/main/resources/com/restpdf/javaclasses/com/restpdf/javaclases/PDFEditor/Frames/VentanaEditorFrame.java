package com.restpdf.javaclases.PDFEditor.Frames;


import com.restpdf.javaclases.PDFBuilder.*;
import com.restpdf.javaclases.PDFEditor.Handlers.PDFWindowHandler;
import com.restpdf.javaclases.PDFEditor.Listeners.PDFEvent;
import com.restpdf.javaclases.PDFEditor.Listeners.ViewPDFListeners;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;
import com.restpdf.javaclases.PDFEditor.Tools.FieldRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class VentanaEditorFrame extends JFrame {    //ventanaPrincipal
    JPanel PanelNuevosCampos, PanelOpciones, PanelCentro;
    JButton BotonGuardarCampos;
    PDFWindowHandler PDFWHandler;
    PDFViewHandler PDFVHandler;
    private JDesktopPane zonaEscritorio;
    private String nombrepdf;
    PDFInternalFrame pdf_if;
    public VentanaEditorFrame(String pdfname){
        nombrepdf = pdfname;
        initSwingComponents();

        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();
        PDFVHandler = new PDFViewHandler();

        pdf_if = new PDFInternalFrame(nombrepdf);

        pdf_if.addInternalFrameListener(PDFWHandler);
        pdf_if.getPanelpdf().addEventListener(PDFVHandler);

        //PanelPDF.add(pdf_if);
        zonaEscritorio.add(pdf_if);

        pdf_if.setSize(new Dimension(zonaEscritorio.getWidth()-100,zonaEscritorio.getHeight()-100));
        pdf_if.setClosable(false);
        pdf_if.setResizable(false);

        pdf_if.setIconifiable(false);
        pdf_if.setVisible(true);
    }
    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.BLACK);
        zonaEscritorio.setPreferredSize(new Dimension(2500,700));

        PanelCentro.add(zonaEscritorio,BorderLayout.CENTER);

////--------------------------------------------------------------------------
        PanelOpciones = new JPanel();
        PanelOpciones.setLayout(new GridLayout(2,0));

        PanelNuevosCampos = new JPanel();
        PanelNuevosCampos.setLayout(new BoxLayout(PanelNuevosCampos, BoxLayout.Y_AXIS));

        JScrollPane jsp = new JScrollPane(PanelNuevosCampos);
        PanelOpciones.add(jsp,BorderLayout.NORTH);

        BotonGuardarCampos = new JButton();
        BotonGuardarCampos.setText("Guardar");
        BotonGuardarCampos.setSize(50,50);
        BotonGuardarCampos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ESTO NO ESTÁ HECHO. DALE UN TIENTO
//                PrintWriter pout = new PrintWriter(System.out);
//                PDF p = new PDF(pout);
//                p.writePDF();
            }
        });
        PanelOpciones.add(BotonGuardarCampos, BorderLayout.SOUTH);
        PanelOpciones.setPreferredSize(new Dimension(200,210));

        this.getContentPane().add(PanelCentro,BorderLayout.CENTER);
        this.getContentPane().add(PanelOpciones,BorderLayout.WEST);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////--------------------------------------------------------------------------
//        PanelPDF = new JPanel();
//
//        PanelPDF.setBorder(BorderFactory.createLineBorder(Color.gray));
//        PanelPDF.setForeground(Color.lightGray);
//        PanelPDF.setLayout(new BorderLayout());
//        PanelPDF.setSize(new Dimension(297,210));
//
//        zonaEscritorio = new JDesktopPane();
//        zonaEscritorio.setBackground(Color.BLACK);
//        zonaEscritorio.setPreferredSize(new Dimension(600, 1000));
//        PanelPDF.add(zonaEscritorio);
//
////--------------------------------------------------------------------------
//        MainInternalFrame.add(PanelOpciones, BorderLayout.WEST);
//        MainInternalFrame.add(PanelPDF);
//
//        this.add(MainInternalFrame);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(new Dimension(3970,2100));

       //panelHerramientasSuperior.add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

    pack();

    }

    private class PDFViewHandler implements ViewPDFListeners {   //manejadorLienzo

        @Override
        public void FieldSelected(PDFEvent evt) {
            ViewPDFPanel view = pdf_if.getPanelpdf();

            if(evt.getFieldSelected() != null){
                view.updateFieldSelected(evt.getFieldSelected());
                view.EnableDeleteListener();
            }

        }
        public void FieldAdded(PDFEvent evt){
            addNuevoCampo(evt.getFieldSelected());
        }
        public void addNuevoCampo(FieldRectangle f){
            JLabel ncampo = new JLabel("introduce nombre del nuevo campo: ");
            JTextField nuevoCampo = new JTextField("nuevo campo...");

            JPanel jp = new JPanel();
            jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
            jp.setPreferredSize(new Dimension(100,40));

            jp.add(ncampo);
            jp.add(nuevoCampo);

            PanelNuevosCampos.add(jp);
        }
    }

}
