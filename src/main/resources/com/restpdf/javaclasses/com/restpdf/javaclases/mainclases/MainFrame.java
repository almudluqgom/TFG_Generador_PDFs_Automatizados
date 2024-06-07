package com.restpdf.javaclases.mainclases;

import com.restpdf.javaclases.PDFEditor.Frames.EditarPDFFrame;
import com.restpdf.javaclases.PDFEditor.Frames.RellenarPDFFrame;
import com.restpdf.javaclases.bdclases.BDFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    JPanel mainPanel,
            RBPanel,
            ButtonPanel;
    JRadioButton RBEditarPDF, RBModificaBD, RBFillForm;
    JButton jb;

    public MainFrame() throws SQLException, ClassNotFoundException {
        initSwingComponents();

    }
    private void initSwingComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        //-------------------------------------------------------------------------------------------
        RBPanel = new JPanel();
        RBPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);

        RBEditarPDF = new JRadioButton();
        RBModificaBD = new JRadioButton();
        RBFillForm = new JRadioButton();


        ButtonGroup G1 = new ButtonGroup();
        RBEditarPDF.setText("Añadir campos a un PDF registrado");
        RBEditarPDF.setBounds(120, 30, 120, 50);
        RBFillForm.setText("Rellenar un PDF");
        RBFillForm.setBounds(120, 30, 120, 50);
        RBModificaBD.setText("Modificar base de datos");
        RBModificaBD.setBounds(250, 30, 80, 50);

        RBPanel.add(RBEditarPDF);
        RBPanel.add(RBModificaBD);
        RBPanel.add(RBFillForm);

        G1.add(RBEditarPDF);
        G1.add(RBFillForm);
        G1.add(RBModificaBD);

        mainPanel.add(RBPanel);

        ButtonPanel = new JPanel();
        ButtonPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);

        jb = new JButton("Selecciona");
        jb.setBounds(125, 90, 80, 30);
        jb.addActionListener(new ActionListener() {            // Anonymous class.

            public void actionPerformed(ActionEvent e)
            {

                // If condition to check if jRadioButton2 is selected.
                if (RBEditarPDF.isSelected()) {
                    EditarPDFFrame mainFrame = null;
                    try {
                        mainFrame = new EditarPDFFrame();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainFrame.setVisible(true);
                }
                if (RBFillForm.isSelected()) {
                    RellenarPDFFrame mainFrame = null;
                    try {
                        mainFrame = new RellenarPDFFrame();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainFrame.setVisible(true);
                }
                else if (RBModificaBD.isSelected()) {
                    BDFrame mainFrame = null;
                    try {
                        mainFrame = new BDFrame();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainFrame.setVisible(true);
                }
                dispose();
            }
        });
        ButtonPanel.add(jb);
        mainPanel.add(ButtonPanel, BorderLayout.SOUTH);

    //-------------------------------------------------------------------------------------------
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);

    }

}
