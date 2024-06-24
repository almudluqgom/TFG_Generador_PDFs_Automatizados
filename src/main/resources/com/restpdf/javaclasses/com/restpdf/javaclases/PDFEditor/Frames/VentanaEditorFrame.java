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

public class VentanaEditorFrame extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel PanelNuevosCampos, PanelCentro, panelHerramientasSuperior ;
    BotonPersonalizado bZoomIN, bZoomOUT, bPrev, bNext,BotonGuardarCampos;
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
    float factormultiplic;

    public VentanaEditorFrame(String pdfname) {
        this.setTitle("Añadiendo campos a " + pdfname);
        nombrepdf = pdfname;
        initSwingComponents();

        PDFWHandler = new PDFWindowHandler();
        PDFVHandler = new PDFViewHandler();

        pdf_if = new PDFInternalFrame(nombrepdf);
        factormultiplic= pdf_if.getFactormultiplic();
        AddBotones(bHerram);

        pdf_if.addInternalFrameListener(PDFWHandler);
        pdf_if.getPanelpdf().addEventListener(PDFVHandler);

        pagecounter.setText("page 1 of " + pdf_if.pages.size());
        currentpnumber = 1;

        zonaEscritorio.add(pdf_if);

        pdf_if.setSize(new Dimension((int) (screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.91)));
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
        double dif1 = fondoAux.getWidth() * fondoAux.getHeight();
        double dif2 = zonaEscritorio.getWidth() * zonaEscritorio.getHeight();

        if (dif2 >= dif1) {
            pdf_if.setSize(new Dimension(fondoAux.getWidth(), fondoAux.getHeight()));
        }

        zonaEscritorio.setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.91)));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenSize);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.DARK_GRAY);

        PanelCentro.add(zonaEscritorio, BorderLayout.CENTER);

////--------------------------------------------------------------------------
        buttonGroup1 = new ButtonGroup();
        PanelNuevosCampos = new JPanel();
        PanelNuevosCampos.setLayout(new BoxLayout(PanelNuevosCampos, BoxLayout.Y_AXIS));
        Dimension pdim = new Dimension((int) (screenSize.getWidth() / 8), (int) screenSize.getHeight());
        PanelNuevosCampos.setPreferredSize(pdim);

        JScrollPane jsp = new JScrollPane(PanelNuevosCampos);
        jsp.getVerticalScrollBar().setUnitIncrement(16);

        this.getContentPane().add(jsp, BorderLayout.WEST);
        this.getContentPane().add(PanelCentro, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////--------------------------------------------------------------------------
        panelHerramientasSuperior = new JPanel();
        panelHerramientasSuperior.setLayout(new BorderLayout());
        bHerram = new JToolBar();

        panelHerramientasSuperior.add(bHerram);
        ////--------------------------------------------------------------------------
        this.getContentPane().add(panelHerramientasSuperior, BorderLayout.PAGE_START);
    }

    public void AddBotones(JToolBar barraHerrm) {

        BotonGuardarCampos = new BotonPersonalizado();
        BotonGuardarCampos.setText("Guardar");
        BotonGuardarCampos.setStyle(ColorStyle.STYLE1);

        BotonGuardarCampos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BDForms bd = new BDForms();
                for (CampoF c : campos) {
                    try {
                        c.setWidth((int) (c.getWidth() * (1/factormultiplic)));
                        c.setHeight((int) (c.getHeight() * (1/factormultiplic)));
                        c.setPosX((int) (c.getPosX() * (1/factormultiplic)));
                        c.setPosY((int) (c.getPosY() * (1/factormultiplic)));
                        bd.setNuevoCampoPDF(c);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error en el guardado del campo " + c.getIndexField() + ". Por favor vuelve a intentarlo");
                        throw new RuntimeException(ex);
                    }
                }
                EditarPDFFrame mainFrame = null;
                try {
                    mainFrame = new EditarPDFFrame();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Cambios guardados con éxito");
                mainFrame.setVisible(true);
                dispose();
            }
        });

        bZoomIN = new BotonPersonalizado();
        bZoomIN.setText("+");
        bZoomIN.setStyle(ColorStyle.STYLE3);

        bZoomIN.setFocusable(false);
        bZoomIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factormultiplic= (float) (factormultiplic*1.15);
                AffineTransform at = AffineTransform.getScaleInstance(1.15, 1.15);
                pdf_if.zoomPage(campos, fondoAux, currentpnumber, at);
               // applyZoom(1.15);
                pdf_if.getPanelpdf().addEventListener(PDFVHandler);
                fondoAux = pdf_if.getPanelpdf().getImagenFondoFormulario(false);

                Dimension oldd = new Dimension(pdf_if.getWidth(), pdf_if.getHeight());

                if ((pdf_if.getWidth() <= fondoAux.getWidth()) &&
                        (pdf_if.getWidth() < (screenSize.getWidth() * 0.88)) &&
                        (fondoAux.getWidth() < (screenSize.getWidth() * 0.88))) {
                    pdf_if.setSize(new Dimension(fondoAux.getWidth(), (int) (screenSize.getHeight() * 0.88)));
                } else if (fondoAux.getWidth() >= (screenSize.getWidth() * 0.88)) {
                    pdf_if.setSize(new Dimension((int) (screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.88)));
                } else {
                    pdf_if.setSize(oldd);
                }
            }
        });

        bZoomOUT = new BotonPersonalizado();
        bZoomOUT.setText("-");
        bZoomOUT.setStyle(ColorStyle.STYLE3);
        bZoomOUT.setFocusable(false);
        bZoomOUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factormultiplic= (float) (factormultiplic*0.85);
                AffineTransform at = AffineTransform.getScaleInstance(0.85, 0.85);
                pdf_if.zoomPage(campos, fondoAux, currentpnumber, at);
                //applyZoom(0.85);
                pdf_if.getPanelpdf().addEventListener(PDFVHandler);

                fondoAux = pdf_if.getPanelpdf().getImagenFondoFormulario(false);
                Dimension oldd = new Dimension(pdf_if.getWidth(), pdf_if.getHeight());

                if (pdf_if.getWidth() >= fondoAux.getWidth()) {
                    pdf_if.setSize(new Dimension(fondoAux.getWidth(), fondoAux.getHeight()));
                } else {
                    pdf_if.setSize(oldd);
                }
            }
        });

        BotonPersonalizado JUndo = new BotonPersonalizado();
        JUndo.setText("Deshacer");
        JUndo.setStyle(ColorStyle.STYLE3);
        JUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pdf_if.getPanelpdf().deleteLastRect();
                pdf_if.setClearBackground();
            }
        });

        //RESET BUTTON FOR DEBUGGING PURPOSES. Dont uncomment it unlees needed
//        JButton JReset = new JButton("Debug: resetea");
//        JReset.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pdf_if.getPanelpdf().setEditMode(true);
//            }
//        });
        pagecounter = new JLabel();

        bPrev = new BotonPersonalizado();
        bPrev.setText("Página anterior");
        bPrev.setStyle(ColorStyle.STYLE2);

        bNext = new BotonPersonalizado();
        bNext.setText("Página siguiente");
        bNext.setStyle(ColorStyle.STYLE2);

        bPrev.setFocusable(false);
        bNext.setFocusable(false);

        if (currentpnumber == 1) {
            bPrev.setEnabled(false);
        }
        if (currentpnumber == pdf_if.pages.size()) {
            bNext.setEnabled(false);
        }
        bPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentpnumber - 1 > 0) {
                    currentpnumber = currentpnumber - 1;
                    pagecounter.setText("page " + currentpnumber + " of " + pdf_if.pages.size());

                    pdf_if.showPage(currentpnumber, campos);
                    pdf_if.getPanelpdf().addEventListener(PDFVHandler);
                }
                if (currentpnumber == 1) {
                    bNext.setEnabled(true);
                    bPrev.setEnabled(false);
                }
            }
        });
        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentpnumber + 1 <= pdf_if.pages.size()) {
                    currentpnumber = currentpnumber + 1;
                    pagecounter.setText("page " + currentpnumber + " of " + pdf_if.pages.size());

                    pdf_if.showPage(currentpnumber, campos);
                    pdf_if.getPanelpdf().addEventListener(PDFVHandler);
                }
                if (currentpnumber == pdf_if.pages.size()) {
                    bPrev.setEnabled(true);
                    bNext.setEnabled(false);
                }
            }
        });

        barraHerrm.add(BotonGuardarCampos);
        barraHerrm.add(Box.createHorizontalStrut(500));

        barraHerrm.add(JUndo);
        barraHerrm.add(Box.createHorizontalStrut(50));

        barraHerrm.add(bZoomIN);
        barraHerrm.add(bZoomOUT);
        barraHerrm.add(Box.createHorizontalStrut(600));


        barraHerrm.add(pagecounter);
        barraHerrm.add(Box.createHorizontalStrut(10));

        barraHerrm.add(bPrev);
        barraHerrm.add(bNext);
    }
    private class PDFViewHandler implements ViewPDFListeners {   //manejadorLienzo

        @Override
        public void FieldSelected(PDFEvent evt) {
            ViewPDFPanel view = pdf_if.getPanelpdf();

            if (evt.getFieldSelected() != null) {
                view.updateFieldSelected(evt.getFieldSelected(), true);
                view.setFieldSelected(evt.getFieldSelected());
                view.EnableDeleteListener(evt.getFieldSelected());
            }
        }
        public void FieldUnSelected(PDFEvent evt) {
            ViewPDFPanel view = pdf_if.getPanelpdf();

            if (evt.getFieldSelected() != null) {
                view.updateFieldSelected(evt.getFieldSelected(), false);
            }
        }

        public void FieldAdded(PDFEvent evt) {
            addNuevoCampo(evt.getFieldSelected(), buttonGroup1);
        }

        @Override
        public void FieldDeleted(PDFEvent e) {
            int indice = e.getIndex();

            ReordenarEtiquetas(indice);
            for (int i = 0; i < PanelNuevosCampos.getComponentCount(); i++) {
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
            for (int i = indice; i < PanelNuevosCampos.getComponentCount(); i++) {
                ((JRadioButton) ((JPanel) PanelNuevosCampos.getComponent(i)).getComponent(0)).setText(String.valueOf(i));
                ((JButton) ((JPanel) PanelNuevosCampos.getComponent(i)).getComponent(1)).setText("Borrar Campo " + String.valueOf(i));
            }
        }

        public void addNuevoCampo(FieldRectangle f, ButtonGroup buttonGroup1) {

            CampoF nuevoF = new CampoF("",
                    nombrepdf,
                    currentpnumber,
                    (int) (f.getRectangulo().getX()),
                    (int) (f.getRectangulo().getY()),
                    (int) (f.getRectangulo().getWidth()),
                    (int) (f.getRectangulo().getHeight()),
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

                BotonPersonalizado botond = new BotonPersonalizado();
                botond.setText("Borrar Campo " + String.valueOf(campos.size()));
                botond.setStyle(ColorStyle.STYLE2);
                botond.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int indice = Integer.parseInt(bu.getText()) - 1;

                        campos.remove(indice);

                        ReordenarEtiquetas(indice);
                        for (int i = 0; i < PanelNuevosCampos.getComponentCount(); i++) {
                            if (i == indice)
                                PanelNuevosCampos.remove(i);
                        }
                        PanelNuevosCampos.revalidate();
                        PanelNuevosCampos.repaint();

                        pdf_if.getPanelpdf().deletefield(indice);
                        pdf_if.setClearBackground();
                    }
                });
                JTextField nuevoCampo = new JTextField("escribe el nombre del campo...");
                nuevoCampo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String t = nuevoCampo.getText();
                        int index = Integer.parseInt(bu.getText());
                        campos.get(index - 1).setNameField(t);
                    }
                });

                JPanel jp = new JPanel();
                jp.setLayout(null);
                jp.setPreferredSize(new Dimension(200, 60));
                jp.setBorder(BorderFactory.createLineBorder(Color.lightGray));

                bu.setBounds(0, 10, 40, 20);
                jp.add(bu);
                buttonGroup1.add(bu);
                botond.setBounds(50, 10, 90, 30);
                jp.add(botond);
                nuevoCampo.setBounds(10, 60, 200, 50);
                jp.add(nuevoCampo);

                PanelNuevosCampos.add(jp);
                PanelNuevosCampos.revalidate();
                PanelNuevosCampos.repaint();
            }
        }

    }
}