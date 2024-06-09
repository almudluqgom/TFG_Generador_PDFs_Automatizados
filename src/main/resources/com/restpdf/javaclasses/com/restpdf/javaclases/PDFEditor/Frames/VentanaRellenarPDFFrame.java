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

    List<CampoF> campos = new ArrayList<>();

    public VentanaRellenarPDFFrame(String pdfname) {
        nombrepdf = pdfname;
        initSwingComponents();

        pdf_if = new PDFillInternalFrame(nombrepdf);
        zonaEscritorio.add(pdf_if);

        pagecounter.setText("page 1 of " + pdf_if.pages.size());
        currentpnumber = 1;

        inicializarListaCampos();

        pdf_if.setSize(new Dimension((int) (screenSize.getWidth() * 0.85), (int) (screenSize.getHeight() * 0.85)));
        pdf_if.setClosable(false);
        pdf_if.setResizable(false);
        pdf_if.setIconifiable(false);
        pdf_if.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenSize);

        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.out.println("Closed");
                RellenarPDFFrame mainFrame = null;
                try {
                    mainFrame = new RellenarPDFFrame();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                mainFrame.setVisible(true);
               dispose();
            }
        });
        //pack();
    }

    private void initSwingComponents() {

        PanelCentro = new JPanel();
        PanelCentro.setLayout(new BorderLayout());

        zonaEscritorio = new JDesktopPane();
        zonaEscritorio.setBackground(Color.BLACK);
        zonaEscritorio.setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.8), (int) (screenSize.getHeight() * 0.8)));

        PanelCentro.add(zonaEscritorio, BorderLayout.CENTER);
////--------------------------------------------------------------------------

        PanelCampos = new JPanel();
        PanelCampos.setLayout(new BoxLayout(PanelCampos, BoxLayout.Y_AXIS));
        Dimension pdim = new Dimension((int) (screenSize.getWidth() / 8), (int) screenSize.getHeight());
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
                currentpnumber = currentpnumber + 1;
                pagecounter.setText("page " + currentpnumber + " of " + pdf_if.pages.size());
            }
        });
        bHerram.add(bPrev);

        bNext = new JButton("previous page");
        bNext.setFocusable(false);
        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentpnumber - 1 > 0) {
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

        this.getContentPane().add(panelHerramientasSuperior, BorderLayout.PAGE_START);
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
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
                    dibujaCampoenLienzo(nuevoc);
                }
            } else {
                int input = JOptionPane.showOptionDialog(null, "El PDF seleccionado no contiene campos. ¿Desea añadir campos nuevos?", "PDF Sin campos",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    // do something
                } else {
                    RellenarPDFFrame mainFrame = null;
                    try {
                        mainFrame = new RellenarPDFFrame();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainFrame.setVisible(true);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Por favor selecciona otro pdf de la lista");


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
        campos.add(c);

        PanelCampos.add(jp);
        PanelCampos.revalidate();
        PanelCampos.repaint();
    }
}
