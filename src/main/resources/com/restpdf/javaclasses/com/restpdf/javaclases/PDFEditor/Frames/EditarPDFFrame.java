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


public class EditarPDFFrame extends JFrame {

    private JPanel mainPanel;
    JLabel nombrep;
    String selectedpdf;
    private static final String website = "https://tfgbd.000webhostapp.com/SelectPDF.php";

    public EditarPDFFrame() throws SQLException, ClassNotFoundException {

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

        mainPanel.add(optionsP, BorderLayout.NORTH);

        JButton b = new JButton("Procesar PDF");
        butP.add(b);

        b.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaEditorFrame mainFrame = null;
                mainFrame = new VentanaEditorFrame(selectedpdf);
                mainFrame.setVisible(true);
                dispose();
            }
        });

        butP.add(nombrep);
        butP.setSize(300, 100);

        JButton back = new JButton("Atrás");
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


        mainPanel.add(butP, BorderLayout.CENTER);
        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
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

        } catch (Exception e) {     e.printStackTrace();        }
    }

}

