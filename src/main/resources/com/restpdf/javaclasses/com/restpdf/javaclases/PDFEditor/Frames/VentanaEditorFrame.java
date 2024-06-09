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
    List<CampoF> campos = new ArrayList<>();
    public VentanaEditorFrame(String pdfname){
        nombrepdf = pdfname;
        initSwingComponents();

        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();
        PDFVHandler = new PDFViewHandler();

        pdf_if = new PDFInternalFrame(nombrepdf);

        pdf_if.addInternalFrameListener(PDFWHandler);
        pdf_if.getPanelpdf().addEventListener(PDFVHandler);

        pagecounter.setText("page 1 of "+ pdf_if.pages.size());
        currentpnumber = 1;

        zonaEscritorio.add(pdf_if);

        pdf_if.setSize(new Dimension((int) (screenSize.getWidth() * 0.85), (int) (screenSize.getHeight() * 0.85)));
        pdf_if.setClosable(false);
        pdf_if.setResizable(false);
        pdf_if.setIconifiable(false);
        pdf_if.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenSize);
       // pack();
    }
    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.BLACK);
        zonaEscritorio.setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.8), (int) (screenSize.getHeight() * 0.8)));

        PanelCentro.add(zonaEscritorio,BorderLayout.CENTER);

////--------------------------------------------------------------------------

        PanelNuevosCampos = new JPanel();
        PanelNuevosCampos.setLayout(new BoxLayout(PanelNuevosCampos, BoxLayout.Y_AXIS));
        Dimension pdim = new Dimension((int) (screenSize.getWidth()/8), (int) screenSize.getHeight());
        PanelNuevosCampos.setPreferredSize(pdim);

        JScrollPane jsp = new JScrollPane(PanelNuevosCampos);
        jsp.getVerticalScrollBar().setUnitIncrement(16);

        this.getContentPane().add(jsp,BorderLayout.WEST);
        this.getContentPane().add(PanelCentro,BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelHerramientasSuperior = new JPanel();
        panelHerramientasSuperior.setLayout(new BorderLayout());
        bHerram = new JToolBar();

        bZoomIN = new JButton("+");
        bZoomIN.setFocusable(false);
        bZoomIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
                aplicarZoom(at);
            }
        });
        bHerram.add(bZoomIN);

        bZoomOUT = new JButton("-");
        bZoomOUT.setFocusable(false);
        bZoomOUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);
                aplicarZoom(at);
            }
        });
        bHerram.add(bZoomOUT);

        pagecounter = new JLabel();
        bHerram.add(pagecounter);

        bPrev = new JButton("next page");
        bPrev.setFocusable(false);
        bPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentpnumber = currentpnumber +1;
                pagecounter.setText("page " + currentpnumber + " of " +  pdf_if.pages.size());
            }
        });
        bHerram.add(bPrev);

        bNext = new JButton("previous page");
        bNext.setFocusable(false);
        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( currentpnumber -1 > 0) {
                    currentpnumber = currentpnumber - 1;
                    pagecounter.setText("page " + currentpnumber + " of " + pdf_if.pages.size());
                }
            }
        });
        bHerram.add(bNext);
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
                //ESTO NO ESTÁ HECHO. DALE UN TIENTO
//                PrintWriter pout = new PrintWriter(System.out);
//                PDF p = new PDF(pout);
//                p.writePDF();
            }
        });
        panelHerramientasSuperior.add(BotonGuardarCampos, BorderLayout.WEST);
        this.getContentPane().add(panelHerramientasSuperior,BorderLayout.PAGE_START);
        pack();
    }

    private void aplicarZoom(AffineTransform at) {      //NO FUNCIONA, OLE
        PDFInternalFrame pdfif = (PDFInternalFrame) (zonaEscritorio.getSelectedFrame());
        if (pdfif instanceof PDFInternalFrame) {
            BufferedImage img = pdfif.getImagen();
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

            CampoF nuevoF = new CampoF("",
                    nombrepdf,
                    currentpnumber,
                    (int) f.getRectangulo().getX(),
                    (int) f.getRectangulo().getY(),
                    (int) f.getRectangulo().getHeight(),
                    (int) f.getRectangulo().getWidth(),
                    campos.size());

            campos.add(nuevoF);

            JLabel ncampo = new JLabel(String.valueOf(campos.size()));
            JTextField nuevoCampo = new JTextField("escribe el nombre del campo...");
            nuevoCampo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String t = nuevoCampo.getText();
                    int index = Integer.parseInt(ncampo.getText());
                    campos.get(index-1).setNameField(t);
                }
            });

            nuevoCampo.setPreferredSize(new Dimension(20,10));

            JPanel jp = new JPanel();
            jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
            jp.setPreferredSize(new Dimension(40,40));
            jp.add(ncampo);
            jp.add(nuevoCampo);

            PanelNuevosCampos.add(jp);
            PanelNuevosCampos.revalidate();
            PanelNuevosCampos.repaint();
        }
    }

}