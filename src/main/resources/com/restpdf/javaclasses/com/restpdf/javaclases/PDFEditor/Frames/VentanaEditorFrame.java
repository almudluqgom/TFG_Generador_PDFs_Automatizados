package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Handlers.*;
import com.restpdf.javaclases.PDFEditor.InternalFrames.*;
import com.restpdf.javaclases.PDFEditor.Listeners.*;
import com.restpdf.javaclases.PDFEditor.Panels.*;
import com.restpdf.javaclases.PDFEditor.Tools.*;
import com.restpdf.javaclases.bdclases.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class VentanaEditorFrame extends JFrame {    //ventanaPrincipal
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel PanelNuevosCampos, PanelCentro, panelHerramientasSuperior ;
    JButton BotonGuardarCampos, bZoomIN, bZoomOUT, bPrev, bNext;
    BufferedImage fondoAux;
    JToolBar bHerram;
    PDFWindowHandler PDFWHandler;
    PDFViewHandler PDFVHandler;
    private JDesktopPane zonaEscritorio;
    private String nombrepdf;
    JLabel pagecounter;
    PDFInternalFrame pdf_if;
    int currentpnumber;
    ButtonGroup buttonGroup1;
    List<CampoF> campos = new ArrayList<>();
    public VentanaEditorFrame(String pdfname){
        nombrepdf = pdfname;
        initSwingComponents();

        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();
        PDFVHandler = new PDFViewHandler();

        pdf_if = new PDFInternalFrame(nombrepdf);
        AddBotones(bHerram);

        pdf_if.addInternalFrameListener(PDFWHandler);
        pdf_if.getPanelpdf().addEventListener(PDFVHandler);

        pagecounter.setText("page 1 of "+ pdf_if.pages.size());
        currentpnumber = 1;

        zonaEscritorio.add(pdf_if);

        //pdf_if.setSize(new Dimension((int) (screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.91)));
        pdf_if.setClosable(false);
        pdf_if.setResizable(false);
        pdf_if.setIconifiable(false);
        pdf_if.setVisible(true);
        try {
            pdf_if.setMaximum(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        fondoAux = pdf_if.getImagen(false);
        pdf_if.setPreferredSize(new Dimension(fondoAux.getWidth(), fondoAux.getHeight()));
        zonaEscritorio.setPreferredSize(new Dimension(fondoAux.getWidth(), fondoAux.getHeight()));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenSize);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.DARK_GRAY);

        PanelCentro.add(zonaEscritorio,BorderLayout.CENTER);

////--------------------------------------------------------------------------
        buttonGroup1 = new ButtonGroup();
        PanelNuevosCampos = new JPanel();
        PanelNuevosCampos.setLayout(new BoxLayout(PanelNuevosCampos, BoxLayout.Y_AXIS));
        Dimension pdim = new Dimension((int) (screenSize.getWidth()/8), (int) screenSize.getHeight());
        PanelNuevosCampos.setPreferredSize(pdim);

        JScrollPane jsp = new JScrollPane(PanelNuevosCampos);
        jsp.getVerticalScrollBar().setUnitIncrement(16);

        this.getContentPane().add(jsp,BorderLayout.WEST);
        this.getContentPane().add(PanelCentro,BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////--------------------------------------------------------------------------
        panelHerramientasSuperior = new JPanel();
        panelHerramientasSuperior.setLayout(new BorderLayout());
        bHerram = new JToolBar();

        panelHerramientasSuperior.add(bHerram, BorderLayout.EAST);

        BotonGuardarCampos = new JButton();
        BotonGuardarCampos.setText("Guardar");
        BotonGuardarCampos.setBounds(120, 30, 120, 50);
        BotonGuardarCampos.setPreferredSize(new Dimension(100,50));
        BotonGuardarCampos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BDForms bd = new BDForms();
                for (CampoF f : campos){
                    try {
                        bd.setNuevoCampoPDF(f);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                //acabar

            }
        });
        panelHerramientasSuperior.add(BotonGuardarCampos, BorderLayout.WEST);
   ////--------------------------------------------------------------------------
        this.getContentPane().add(panelHerramientasSuperior,BorderLayout.PAGE_START);
        //pack();
    }

    private void aplicarZoom(AffineTransform at) {      //NO FUNCIONA, OLE
        PDFInternalFrame pdfif = (PDFInternalFrame) (zonaEscritorio.getSelectedFrame());
        if (pdfif instanceof PDFInternalFrame) {
           // switchVolcadoActionPerformed(null);
            BufferedImage img = pdfif.getImagen(false);
            if (img != null) {
                try {
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    pdfif.setImagen(imgdest);
                    ((PDFInternalFrame)pdfif).getPanelpdf().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println("Ha ocurrido un error al aplicar zoom");
                    JOptionPane.showMessageDialog(new JFrame(), "Error al aplicar zoom ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void AddBotones(JToolBar barraHerrm){

        bZoomIN = new JButton("+");
        bZoomIN.setFocusable(false);
        bZoomIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
                aplicarZoom(at);
            }
        });
        barraHerrm.add(bZoomIN);

        bZoomOUT = new JButton("-");
        bZoomOUT.setFocusable(false);
        bZoomOUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);
                aplicarZoom(at);
            }
        });
        barraHerrm.add(bZoomOUT);
        JButton JUndo = new JButton("Deshacer");
        JUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pdf_if.getPanelpdf().deleteLastRect();
                pdf_if.setClearBackground();
            }
        });
        barraHerrm.add(JUndo);

        JButton JReset = new JButton("Debug: resetea");
        JReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdf_if.getPanelpdf().setEditMode(true);
            }
        });
        barraHerrm.add(JReset);
        pagecounter = new JLabel();
        barraHerrm.add(pagecounter);

        bPrev = new JButton("Previous page");
        bNext = new JButton("Next page");

        bPrev.setFocusable(false);
        bNext.setFocusable(false);

        barraHerrm.add(bPrev);
        barraHerrm.add(bNext);

        if( currentpnumber == 1) {
            bPrev.setEnabled(false);
        }
        if( currentpnumber == pdf_if.pages.size()) {
            bNext.setEnabled(false);
        }
        bPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if( currentpnumber -1 > 0) {
                    currentpnumber = currentpnumber - 1;
                    pagecounter.setText("page " + currentpnumber + " of " + pdf_if.pages.size());
                    pdf_if.showPage(currentpnumber, campos);
                    pdf_if.getPanelpdf().addEventListener(PDFVHandler);
                }
                if( currentpnumber == 1) {
                    bNext.setEnabled(true);
                    bPrev.setEnabled(false);
                }
            }
        });
        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( currentpnumber + 1 <= pdf_if.pages.size()) {
                    currentpnumber = currentpnumber + 1;
                    pagecounter.setText("page " + currentpnumber + " of " + pdf_if.pages.size());
                    pdf_if.showPage(currentpnumber, campos);
                    pdf_if.getPanelpdf().addEventListener(PDFVHandler);
                }
                if( currentpnumber == pdf_if.pages.size()) {
                    bPrev.setEnabled(true);
                    bNext.setEnabled(false);
                }
            }
        });

    }
    private class PDFViewHandler implements ViewPDFListeners {   //manejadorLienzo

        @Override
        public void FieldSelected(PDFEvent evt) {
            ViewPDFPanel view = pdf_if.getPanelpdf();

            if(evt.getFieldSelected() != null){
                view.updateFieldSelected(evt.getFieldSelected(),true);
                view.setFieldSelected(evt.getFieldSelected());
                view.EnableDeleteListener(evt.getFieldSelected());
            }
        }
        public void FieldUnSelected(PDFEvent evt) {
            ViewPDFPanel view = pdf_if.getPanelpdf();

            if(evt.getFieldSelected() != null){
                view.updateFieldSelected(evt.getFieldSelected(),false);
            }
        }
        public void FieldAdded(PDFEvent evt){
            addNuevoCampo(evt.getFieldSelected(),buttonGroup1);
        }

        @Override
        public void FieldDeleted(PDFEvent e) {
            int indice = e.getIndex();

            ReordenarEtiquetas(indice);
            for (int i= 0; i< PanelNuevosCampos.getComponentCount();i++){
                if (i == indice)
                    PanelNuevosCampos.remove(i);
            }
                PanelNuevosCampos.revalidate();
                PanelNuevosCampos.repaint();
                campos.remove(indice);

            pdf_if.getPanelpdf().deletefield(indice);
            pdf_if.setClearBackground();
        }

        private void ReordenarEtiquetas(int indice) {
            for (int i= indice; i< PanelNuevosCampos.getComponentCount();i++){
                ((JRadioButton)((JPanel) PanelNuevosCampos.getComponent(i)).getComponent(0)).setText(String.valueOf(i));
                ((JButton)((JPanel) PanelNuevosCampos.getComponent(i)).getComponent(1)).setText("Borrar Campo " + String.valueOf(i));
            }
        }
        public void addNuevoCampo(FieldRectangle f, ButtonGroup buttonGroup1){

            CampoF nuevoF = new CampoF("",
                    nombrepdf,
                    currentpnumber,
                    (int) f.getRectangulo().getX(),
                    (int) f.getRectangulo().getY(),
                    (int) f.getRectangulo().getHeight(),
                    (int) f.getRectangulo().getWidth(),
                    campos.size());

            if ((nuevoF.getWidth() > 10) && (nuevoF.getHeight() > 10)) {
                campos.add(nuevoF);

                JRadioButton bu = new JRadioButton(String.valueOf(campos.size()));
                bu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pdf_if.getPanelpdf().SelectField(Integer.parseInt(bu.getText()));
                    }
                });
                //---------------------
                //JLabel ncampo = new JLabel(String.valueOf(campos.size()));
                JButton botond = new JButton("Borrar Campo " + String.valueOf(campos.size()));
                botond.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       // pdf_if.getPanelpdf().deletefield(Integer.parseInt(bu.getText())-1);
                       // pdf_if.getPanelpdf().deletefield(Integer.parseInt(ncampo.getText())-1);
                        int indice = Integer.parseInt(bu.getText())-1;

                        campos.remove(indice);

                        ReordenarEtiquetas(indice);
                        for (int i= 0; i< PanelNuevosCampos.getComponentCount();i++){
                            if (i == indice)
                                PanelNuevosCampos.remove(i);
                        }
                        PanelNuevosCampos.revalidate();
                        PanelNuevosCampos.repaint();

                        pdf_if.getPanelpdf().deletefield(indice);
                        pdf_if.setClearBackground();
                    }
                });
                //-----------------
                JTextField nuevoCampo = new JTextField("escribe el nombre del campo...");
                nuevoCampo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String t = nuevoCampo.getText();
                        int index = Integer.parseInt(bu.getText());
                        campos.get(index - 1).setNameField(t);
                    }
                });

                nuevoCampo.setPreferredSize(new Dimension(20, 10));

                JPanel jp = new JPanel();
                jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
                jp.setPreferredSize(new Dimension(40, 40));
                jp.add(bu);
                buttonGroup1.add(bu);
                //jp.add(ncampo);
                jp.add(botond);
                jp.add(nuevoCampo);

                PanelNuevosCampos.add(jp);
                PanelNuevosCampos.revalidate();
                PanelNuevosCampos.repaint();
            }
        }

    }
}