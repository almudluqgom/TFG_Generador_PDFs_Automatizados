package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Handlers.PDFWindowHandler;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.bdclases.BDForms;
import com.restpdf.javaclases.bdclases.CampoF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class VentanaRellenarPDFFrame extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel PanelCampos, PanelCentro, panelHerramientasSuperior ;
    JButton BotonGuardarCampos, bZoomIN, bZoomOUT, bPrev, bNext;
    BufferedImage fondoAux;
    JToolBar bHerram;
    PDFWindowHandler PDFWHandler;
    private JDesktopPane zonaEscritorio;
    private String nombrepdf;
    JLabel pagecounter;
    PDFInternalFrame pdf_if;
    int currentpnumber;
    public VentanaRellenarPDFFrame(String pdfname){
        nombrepdf = pdfname;
        initSwingComponents();

        PDFWHandler = new PDFWindowHandler();   //mVentanaInterna = new ManejadorVentanaInterna();

        pdf_if = new PDFInternalFrame(nombrepdf);
        pdf_if.addInternalFrameListener(PDFWHandler);

        pagecounter.setText("page 1 of "+ pdf_if.pages.size());
        currentpnumber = 1;

        zonaEscritorio.add(pdf_if);

        pdf_if.setSize(new Dimension(zonaEscritorio.getWidth()-100,zonaEscritorio.getHeight()-100));
        pdf_if.setClosable(false);
        pdf_if.setResizable(false);
        pdf_if.setIconifiable(false);
        pdf_if.setVisible(true);

        this.setPreferredSize(screenSize);
        pack();
    }

    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.BLACK);
        zonaEscritorio.setPreferredSize(new Dimension(2500,700));

        PanelCentro.add(zonaEscritorio,BorderLayout.CENTER);
////--------------------------------------------------------------------------

        PanelCampos = new JPanel();
        PanelCampos.setLayout(new BoxLayout(PanelCampos, BoxLayout.Y_AXIS));
        Dimension pdim = new Dimension((int) (screenSize.getWidth()/8), (int) screenSize.getHeight());
        PanelCampos.setPreferredSize(pdim);
        inicializarListaCampos();

        JScrollPane jsp = new JScrollPane(PanelCampos);

        this.getContentPane().add(jsp,BorderLayout.WEST);
        this.getContentPane().add(PanelCentro,BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////--------------------------------------------------------------------------
        panelHerramientasSuperior = new JPanel();
        panelHerramientasSuperior.setLayout(new BorderLayout());
        bHerram = new JToolBar();

        bZoomIN = new JButton("+");
        bZoomIN.setFocusable(false);
        bZoomIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
                //aplicarZoom(at);
            }
        });
        bHerram.add(bZoomIN);

        bZoomOUT = new JButton("-");
        bZoomOUT.setFocusable(false);
        bZoomOUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);
               // aplicarZoom(at);
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

    private void inicializarListaCampos() {

        try {

            StringEncoder e = new StringEncoder();
            String s = "https://tfgbd.000webhostapp.com/SelectCamposPDF.php?id=" + e.encripta(nombrepdf);
            URL url = new URL(s);
            URLConnection urlc = url.openConnection();
            urlc.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String str = br.readLine();
            ArrayList<String> listaCampos = new ArrayList<>(Arrays.asList(str.split("<br>")));

            PanelCampos = new JPanel();
            PanelCampos.setLayout(new GridLayout(listaCampos.size(), 0));

            for (String campo : listaCampos) {
                JPanel PanelNuevoC = new JPanel();
                PanelNuevoC.setLayout(new BoxLayout(PanelNuevoC, BoxLayout.Y_AXIS));
                PanelNuevoC.setPreferredSize(new Dimension(40,40));

                CampoF nuevoc = e.transformaStringEnCampo(campo);
                JLabel ncampo = new JLabel(String.valueOf(nuevoc.getNameField()));
                JTextField nuevoCampo = new JTextField("introduce " + nuevoc.getNameField() + "...");
                nuevoCampo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    }
                });

                PanelNuevoC.add(ncampo);
                PanelNuevoC.add(nuevoCampo);
                PanelCampos.add(PanelNuevoC);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
