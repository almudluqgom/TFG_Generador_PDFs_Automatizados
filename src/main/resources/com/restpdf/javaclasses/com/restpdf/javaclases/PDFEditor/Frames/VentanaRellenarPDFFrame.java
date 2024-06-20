package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Tools.PDFCreator;
import com.restpdf.javaclases.PDFEditor.InternalFrames.PDFillInternalFrame;
import com.restpdf.javaclases.PDFEditor.Tools.FieldLine;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.bdclases.CampoF;
import com.restpdf.javaclases.mainclases.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaRellenarPDFFrame extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JPanel PanelCampos, PanelCentro, panelHerramientasSuperior ;
    JButton BotonGuardarCampos, bZoomIN, bZoomOUT, bPrev, bNext;
    BufferedImage fondoAux;
    JToolBar bHerram;
    private JDesktopPane zonaEscritorio;
    private String nombrepdf;
    JLabel pagecounter;
    PDFillInternalFrame pdf_if;
    int currentpnumber;
    ArrayList<CampoF> campos;

    public VentanaRellenarPDFFrame(String pdfname) {
        nombrepdf = pdfname;
        initSwingComponents();

        pdf_if = new PDFillInternalFrame(nombrepdf);
        AddBotones(bHerram);

        campos = new ArrayList<>();
        inicializarListaCampos();

        for (CampoF c : campos) {
            dibujaCampoenLienzo(c);
        }

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

        if (dif2 >= dif1) {            pdf_if.setSize(new Dimension(fondoAux.getWidth(), fondoAux.getHeight()));        }

        zonaEscritorio.setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.91)));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenSize);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.BLACK);

        PanelCentro.add(zonaEscritorio, BorderLayout.CENTER);
////--------------------------------------------------------------------------
        PanelCampos = new JPanel();
        PanelCampos.setLayout(new BoxLayout(PanelCampos, BoxLayout.Y_AXIS));
        Dimension pdim = new Dimension((int) (screenSize.getWidth()/8), (int) screenSize.getHeight());
        PanelCampos.setPreferredSize(pdim);

        JScrollPane jsp = new JScrollPane(PanelCampos);
        jsp.getVerticalScrollBar().setUnitIncrement(16);

        this.getContentPane().add(jsp, BorderLayout.WEST);
        this.getContentPane().add(PanelCentro, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////--------------------------------------------------------------------------
        panelHerramientasSuperior = new JPanel();
        panelHerramientasSuperior.setLayout(new BorderLayout());
        bHerram = new JToolBar();

        panelHerramientasSuperior.add(bHerram, BorderLayout.EAST);


        BotonGuardarCampos = new JButton();
        BotonGuardarCampos.setText("Guardar");
        BotonGuardarCampos.setBounds(120, 30, 120, 50);
        BotonGuardarCampos.setPreferredSize(new Dimension(100, 50));
        BotonGuardarCampos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PDFCreator newversion = new PDFCreator(nombrepdf);
                newversion.addNewTexts(pdf_if.getPanelpdf().getvLines());
                newversion.fillPDF();

                RellenarPDFFrame mainFrame = null;
                try {
                    mainFrame = new RellenarPDFFrame();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Cambios guardados con éxito");
                mainFrame.setVisible(true);
                dispose();
            }
        });
        panelHerramientasSuperior.add(BotonGuardarCampos, BorderLayout.WEST);
        ////--------------------------------------------------------------------------
        this.getContentPane().add(panelHerramientasSuperior, BorderLayout.PAGE_START);
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

            if (!str.contains("0 results")) {
                ArrayList<String> listaCampos = new ArrayList<>(Arrays.asList(str.split("<br>")));
                listaCampos = (ArrayList<String>) listaCampos.stream().distinct().collect(Collectors.toList());

                for (String campo : listaCampos) {
                    CampoF nuevoc = e.transformaStringEnCampo(campo);
                    campos.add(nuevoc);
                    //dibujaCampoenLienzo(nuevoc);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dibujaCampoenLienzo(CampoF c){
        Point2D p1 = new Point2D.Double(c.getPosX(),c.getPosY());
        Point2D p2 = new Point2D.Double(c.getPosX()+c.getWidth(),c.getPosY());

        FieldLine f = new FieldLine(p1,p2);

        JLabel ncampo = new JLabel(c.getNameField());
        JTextField nuevoCampo = new JTextField("escribe el valor de "+c.getNameField() +"...");
        nuevoCampo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setText(nuevoCampo.getText());
                pdf_if.getPanelpdf().repaint();
            }
        });

        nuevoCampo.setPreferredSize(new Dimension(20,10));

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.setPreferredSize(new Dimension(40,40));
        jp.add(ncampo);
        jp.add(nuevoCampo);

        pdf_if.getPanelpdf().addnewLine(f);

        PanelCampos.add(jp);
        PanelCampos.revalidate();
        PanelCampos.repaint();
    }

    public ArrayList<CampoF> getCampos() {
        return campos;
    }
    public void AddBotones(JToolBar barraHerrm){

        bZoomIN = new JButton("+");
        bZoomIN.setFocusable(false);
        bZoomIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(1.15, 1.15);
                pdf_if.zoomPage( campos,fondoAux,currentpnumber,at);
                fondoAux = pdf_if.getPanelpdf().getImagenFondoFormulario(false);

                Dimension oldd = new Dimension(pdf_if.getWidth(), pdf_if.getHeight());

                if((pdf_if.getWidth() <= fondoAux.getWidth()) &&
                        (pdf_if.getWidth() < (screenSize.getWidth()* 0.88)) &&
                        (fondoAux.getWidth() < (screenSize.getWidth()* 0.88))){
                    pdf_if.setSize(new Dimension(fondoAux.getWidth(),(int) (screenSize.getHeight() * 0.88)));
                } else if (fondoAux.getWidth() >= (screenSize.getWidth()* 0.88)) {
                    pdf_if.setSize(new Dimension((int) (screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.88)));
                } else{
                    pdf_if.setSize(oldd);
                }
            }
        });
        barraHerrm.add(bZoomIN);

        bZoomOUT = new JButton("-");
        bZoomOUT.setFocusable(false);
        bZoomOUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AffineTransform at = AffineTransform.getScaleInstance(0.85, 0.85);
                pdf_if.zoomPage( campos,fondoAux,currentpnumber,at);

                fondoAux = pdf_if.getPanelpdf().getImagenFondoFormulario(false);

                Dimension oldd = new Dimension(pdf_if.getWidth(), pdf_if.getHeight());

                if(pdf_if.getWidth()>=fondoAux.getWidth()) {
                    pdf_if.setSize(new Dimension(fondoAux.getWidth(), fondoAux.getHeight()));
                }else{
                    pdf_if.setSize(oldd);
                }
            }
        });
        barraHerrm.add(bZoomOUT);

        pagecounter = new JLabel();
        bHerram.add(pagecounter);

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
                }
                if( currentpnumber == pdf_if.pages.size()) {
                    bPrev.setEnabled(true);
                    bNext.setEnabled(false);
                }
            }
        });
    }
}
