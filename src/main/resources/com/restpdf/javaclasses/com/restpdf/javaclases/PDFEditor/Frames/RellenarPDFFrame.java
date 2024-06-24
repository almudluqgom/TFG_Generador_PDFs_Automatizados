package com.restpdf.javaclases.PDFEditor.Frames;

import com.restpdf.javaclases.PDFEditor.Tools.BotonPersonalizado;
import com.restpdf.javaclases.PDFEditor.Tools.ColorStyle;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.mainclases.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RellenarPDFFrame extends JFrame {

    private JPanel mainPanel;
    JLabel nombrep;
    String selectedpdf;

    public RellenarPDFFrame() throws SQLException, ClassNotFoundException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        this.setTitle("Rellenar datos de un PDF");
        initSwingComponents();
    }

    private void initSwingComponents() {

        mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(3, 0));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        JPanel PanelTex = new JPanel();
        PanelTex.setLayout(null);
        JLabel lb = new JLabel("   Por favor, selecciona alguna de las opciones abajo seleccionadas:");
        PanelTex.add(lb);
        lb.setBounds(20, 20, 400, 20);

        nombrep = new JLabel("PDF seleccionado: ...");
        nombrep.setBounds(20, 70, 400, 20);
        PanelTex.add(nombrep);

        mainPanel.add(PanelTex);
        //-------------------------------------------------------------------------------------------

        JPanel optionsP = new JPanel();
        ButtonGroup buttonGroup1 = new ButtonGroup();

        inicializarListaPDFsDisponibles(optionsP, buttonGroup1, nombrep);
        JScrollPane jp = new JScrollPane(optionsP);
        jp.setBorder(null);
        jp.setPreferredSize(new Dimension(400,450));
        jp.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(jp, BorderLayout.NORTH);
        //-------------------------------------------------------------------------------------------

        JPanel butP = new JPanel();
        butP.setLayout(null);

        BotonPersonalizado b = new BotonPersonalizado();
        b.setText("Procesar PDF");
        b.setStyle(ColorStyle.STYLE1);
        b.setBounds(80, 30, 100, 20);

        butP.add(b);

        b.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRellenarPDFFrame mainFrame = new VentanaRellenarPDFFrame(selectedpdf);
                if(!mainFrame.getCampos().isEmpty()){
                    mainFrame = new VentanaRellenarPDFFrame(selectedpdf);
                    mainFrame.setVisible(true);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "No hay campos disponibles para éste documento. Por favor selecciona otro, o inserta campos en éste formulario");
                }
            }
        });

        BotonPersonalizado back = new BotonPersonalizado();
        back.setText("Volver");
        back.setStyle(ColorStyle.STYLE3);
        back.setBounds(190, 30, 80, 20);
        butP.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = null;

                try {
                    mainFrame = new MainFrame();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                mainFrame.setVisible(true);
                dispose();
            }

        });

        mainPanel.add(butP);
        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
    }

    private void inicializarListaPDFsDisponibles(JPanel panelOp, ButtonGroup buttonGroup1, JLabel nombrep) {

        try {
            URL url = new URL("https://tfgbd.000webhostapp.com/selectPDF.php");
            URLConnection urlc = url.openConnection();

            urlc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String str = br.readLine();

            ArrayList<String> listaPDFsDisp = new ArrayList<>(Arrays.asList(str.split("<br>")));
            listaPDFsDisp = (ArrayList<String>) listaPDFsDisp.stream().distinct().collect(Collectors.toList());

            panelOp.setLayout(new GridLayout(listaPDFsDisp.size(), 0));
            StringEncoder e = new StringEncoder();

            for (String pdf : listaPDFsDisp) {
                if(listaPDFsDisp.indexOf(pdf) == 0){
                    pdf = pdf.replace(" ","");
                }
                pdf = e.desencripta(pdf);
                final JRadioButton button1 = new JRadioButton(pdf);
                panelOp.add(button1);
                buttonGroup1.add(button1);

                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedpdf = button1.getText();
                        nombrep.setText("PDF seleccionado: " + button1.getText());
                    }
                });
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DEMO VERSION - PARA CUANDO LAS BD NO QUIEREN FUNCAR
//        ArrayList<String> listaPDFsDisp = new ArrayList();
//
//        listaPDFsDisp.add("C:\\Users\\Almuchuela\\Desktop\\TestSave\\PDFEnblanco.pdf");
//        listaPDFsDisp.add("C:\\Users\\Almuchuela\\Desktop\\TestSave\\Vinted-S1212467838.pdf");
//       listaPDFsDisp.add("C:\\Users\\Almuchuela\\Downloads\\factura luz.pdf");
//
//        panelOp.setLayout(new GridLayout(listaPDFsDisp.size(), 0));
//        StringEncoder e = new StringEncoder();
//
//        for (String pdf : listaPDFsDisp) {
//            pdf = e.desencripta(pdf);
//            final JRadioButton button1 = new JRadioButton(pdf);
//            panelOp.add(button1);
//            buttonGroup1.add(button1);
//
//            button1.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    selectedpdf = button1.getText();
//                    nombrep.setText("PDF seleccionado: " + button1.getText());
//                }
//            });
//        }
   }

}
