package com.restpdf.javaclases.PDFEditor.Frames;

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
        initSwingComponents();
    }

    private void initSwingComponents() {

        mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));


        JPanel butP = new JPanel(new GridLayout(2, 0));
        nombrep = new JLabel("...");

        JPanel optionsP = new JPanel();
        optionsP.setLayout(new GridLayout());
        ButtonGroup buttonGroup1 = new ButtonGroup();

        inicializarListaPDFsDisponibles(optionsP, buttonGroup1, nombrep);

        JScrollPane jp = new JScrollPane(optionsP);
        jp.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(jp, BorderLayout.NORTH);

        JButton b = new JButton("Rellenar PDF");
        b.setBounds(120, 30, 120, 50);
        b.setPreferredSize(new Dimension(100, 50));
        butP.add(b);

        b.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRellenarPDFFrame mainFrame = null;
                mainFrame = new VentanaRellenarPDFFrame(selectedpdf);
                mainFrame.setVisible(true);
                dispose();
            }
        });

        butP.add(nombrep);
        butP.setSize(300, 100);

        JButton back = new JButton("Atrás");
        butP.add(back);
        butP.setBounds(120, 30, 120, 50);
        butP.setPreferredSize(new Dimension(100, 50));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = null;

                try {
                    mainFrame = new MainFrame();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                mainFrame.setVisible(true);
                dispose();
            }
        });

        mainPanel.add(butP, BorderLayout.CENTER);
        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 600);
    }

    private void inicializarListaPDFsDisponibles(JPanel panelOp, ButtonGroup buttonGroup1, JLabel nombrep) {

//        try {
//            URL url = new URL("https://tfgbd.000webhostapp.com/selectPDF.php");
//            URLConnection urlc = url.openConnection();
//
//            urlc.connect();
//            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
//            String str = br.readLine();
//
//            ArrayList<String> listaPDFsDisp = new ArrayList<>(Arrays.asList(str.split("<br>")));
//            listaPDFsDisp = (ArrayList<String>) listaPDFsDisp.stream().distinct().collect(Collectors.toList());
//
//            panelOp.setLayout(new GridLayout(listaPDFsDisp.size(), 0));
//            StringEncoder e = new StringEncoder();
//
//            for (String pdf : listaPDFsDisp) {
//                pdf = e.desencripta(pdf);
//                final JRadioButton button1 = new JRadioButton(pdf);
//                panelOp.add(button1);
//                buttonGroup1.add(button1);
//
//                button1.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        selectedpdf = button1.getText();
//                        nombrep.setText("PDF seleccionado: " + button1.getText());
//                    }
//                });
//            }
//
//            br.close();
//
//        } catch (Exception e) {     e.printStackTrace();        }
        // DEMO VERSION - PARA CUANDO LAS BD NO QUIEREN FUNCAR
        ArrayList<String> listaPDFsDisp = new ArrayList();

        listaPDFsDisp.add("C:\\Users\\Almuchuela\\Desktop\\TestSave\\PDFEnblanco.pdf");
        listaPDFsDisp.add("C:\\Users\\Almuchuela\\Desktop\\TestSave\\Vinted-S1212467838.pdf");
        listaPDFsDisp.add("C:\\Users\\Almuchuela\\Downloads\\b8cfcc76-9e9e-468d-aa24-3f4ca3dcce7d.pdf");

        panelOp.setLayout(new GridLayout(listaPDFsDisp.size(), 0));
        StringEncoder e = new StringEncoder();

        for (String pdf : listaPDFsDisp) {
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
    }

}
