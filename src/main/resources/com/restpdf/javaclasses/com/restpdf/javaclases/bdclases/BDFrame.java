package com.restpdf.javaclases.bdclases;

import com.restpdf.javaclases.mainclases.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BDFrame extends JFrame {

    BDForms bd;
    JPanel mainPanel, FolderPanel,  FilePanel;
    JButton ChooserFolderButton, NewFolderButton,
            ChooseFileButton, NewFileButton;
    JFileChooser chooser;
    JLabel textfolder, textfile;
    static String choosertitle = "Selecciona la carpeta: ";
    static String filetitle = "Selecciona el archivo: ";

    public BDFrame() throws SQLException, ClassNotFoundException {
        bd = new BDForms();

        initSwingComponents();
    }

    private void initSwingComponents() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4,1));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        FolderPanel = new JPanel(new FlowLayout());
        FolderPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);
        FolderPanel.setSize(300,250);
        textfolder = new JLabel();

        ChooserFolderButton = new JButton("Cargar directorio...");
        ChooserFolderButton.addActionListener(new ActionListener() { //selección de directorio a guardar
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
        FolderPanel.add(ChooserFolderButton);
        FolderPanel.add(textfolder);
        NewFolderButton = new JButton("Selecciona directorio");
        NewFolderButton.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
            @Override
            public void actionPerformed(ActionEvent e) {
                String f = textfolder.getText();

                bd.setCarpeta(f);
                System.out.println("Exito actualizando la base de datos");
                JOptionPane.showMessageDialog(null, "Exito actualizando la base de datos",
                        "InfoBox: Carpeta de trabajo actualizada", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        FolderPanel.add(NewFolderButton);
        mainPanel.add(FolderPanel);

        //----------------------------------------------------------------------

        FilePanel = new JPanel(new FlowLayout());

        FilePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);
        FilePanel.setSize(300,150);

        textfile= new JLabel();
        ChooseFileButton = new JButton("Añadir nuevo PDF a la base de Datos");
        ChooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();

                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);  // disable the "All files" option.
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));


                if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(chooser.getParent())) {
                    System.out.println("You chose to open this file: " +
                            chooser.getSelectedFile().getAbsolutePath());
                    textfile.setText(chooser.getSelectedFile().getAbsolutePath());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });

        FilePanel.add(ChooseFileButton);
        FilePanel.add(textfile);

        NewFileButton = new JButton("Añadir PDF");
        NewFileButton.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
            @Override
            public void actionPerformed(ActionEvent e) {
                String f = textfile.getText();
                String new_mess = new String();

                new_mess = bd.sendNewFile(f);
                System.out.println("Exito actualizando la base de datos");
                JOptionPane.showMessageDialog(null, new_mess, "Resultados", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        FilePanel.add(NewFileButton);
        mainPanel.add(FilePanel);
        //----------------------------------------------------------------------

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,400);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                MainFrame mainFrame = null;
                try {
                    mainFrame = new MainFrame();
                } catch (SQLException | ClassNotFoundException er) {
                    throw new RuntimeException(er);
                }
                mainFrame.setVisible(true);
                e.getWindow().dispose();
            }
        });
    }


}