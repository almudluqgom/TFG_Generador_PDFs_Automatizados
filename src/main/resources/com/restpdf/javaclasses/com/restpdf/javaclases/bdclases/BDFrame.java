package com.restpdf.javaclases.bdclases;

import com.restpdf.javaclases.mainclases.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BDFrame extends JFrame {

    BDForms bd;
    JPanel mainPanel,
            FolderPanel;
    JButton ChooserButton,
            NewFolderButton;
    JFileChooser chooser;
    JLabel textfolder;
    static String choosertitle = "Selecciona la carpeta: ";
    public BDFrame() throws SQLException, ClassNotFoundException {
        bd = new BDForms();

        initSwingComponents();
    }

    private void initSwingComponents() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,1));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        FolderPanel = new JPanel(new FlowLayout());
        FolderPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);

        textfolder = new JLabel();

        ChooserButton = new JButton("Cargar directorio...");
        ChooserButton.addActionListener(new ActionListener() { //selección de directorio a guardar
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                chooser.setAcceptAllFileFilterUsed(false);  // disable the "All files" option.

                if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(chooser.getParent())) {
                    System.out.println("You chose to open this directory: " +
                            chooser.getSelectedFile().getAbsolutePath());
                    textfolder.setText(chooser.getSelectedFile().getAbsolutePath());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        FolderPanel.add(ChooserButton);
        FolderPanel.add(textfolder);
        mainPanel.add(FolderPanel);

        NewFolderButton = new JButton("Selecciona directorio");
        NewFolderButton.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
            @Override
            public void actionPerformed(ActionEvent e) {
                String f = textfolder.getText();

                try {
                    bd.setCarpeta(f);
                    System.out.println("Exito actualizando la base de datos");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Exito actualizando la base de datos",
                        "InfoBox: Carpeta de trabajo actualizada", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        mainPanel.add(NewFolderButton);
        ChooserButton = new JButton("Añadir nuevo PDF a la base de Datos");
        ChooserButton.addActionListener(new ActionListener() { //selección de directorio a guardar
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        mainPanel.add(ChooserButton);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(200,250);

//        FolderPanel.add(goButton);
//        FolderPanel = new JPanel(new FlowLayout());
//        FolderPanel.add(s);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Uncomment following to open another window!");
                MainFrame mainFrame = null;
                try {
                    mainFrame = new MainFrame();
                } catch (SQLException | ClassNotFoundException er) {
                    throw new RuntimeException(er);
                }
                mainFrame.setVisible(true);
                e.getWindow().dispose();
                System.out.println("JFrame Closed!");
            }
        });
    }


}