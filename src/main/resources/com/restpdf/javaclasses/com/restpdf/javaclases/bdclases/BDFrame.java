package com.restpdf.javaclases.bdclases;

import com.restpdf.javaclases.PDFEditor.Tools.BotonPersonalizado;
import com.restpdf.javaclases.PDFEditor.Tools.ColorStyle;
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
    BotonPersonalizado ChooserFolderButton, NewFolderButton,
            ChooseFileButton, NewFileButton;
    JFileChooser chooser;
    JLabel textfolder, textfile;
    static String choosertitle = "Selecciona la carpeta: ";
    static String filetitle = "Selecciona el archivo: ";

    public BDFrame() throws SQLException, ClassNotFoundException {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        this.setTitle("Gestión de base de datos del programa");
        bd = new BDForms();

        initSwingComponents();
    }

    private void initSwingComponents() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,0));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        FolderPanel = new JPanel();
        //FolderPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.LINE_START);
        FolderPanel.setSize(300,250);

        //ChooserFolderButton = new JButton("Cargar directorio...");
        JLabel init = new JLabel("Selección de directorio de guardado de archivos:");
        ChooserFolderButton = new BotonPersonalizado();
        ChooserFolderButton.setText("Selecciona un directorio...");
        ChooserFolderButton.setStyle(ColorStyle.STYLE2);
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
        textfolder = new JLabel("....");


        //NewFolderButton = new JButton("Selecciona directorio");
        NewFolderButton = new BotonPersonalizado();
        NewFolderButton.setText("Guardar directorio");
        NewFolderButton.setStyle(ColorStyle.STYLE1);
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

        FolderPanel.setLayout(null);
        init.setBounds(10,10,300,20);
        ChooserFolderButton.setBounds(80,30,150,20);
        textfolder.setBounds(80,60,100,20);
        NewFolderButton.setBounds(80,80,150,20);

        FolderPanel.add(init);
        FolderPanel.add(ChooserFolderButton);
        FolderPanel.add(textfolder);
        FolderPanel.add(NewFolderButton);

        mainPanel.add(FolderPanel);

        //----------------------------------------------------------------------

        FilePanel = new JPanel();
        FilePanel.setSize(300,150);

        JLabel init2 = new JLabel("Subida de archivos a la nube:");


        //ChooseFileButton = new JButton("Añadir nuevo PDF a la base de Datos");
        ChooseFileButton = new BotonPersonalizado();
        ChooseFileButton.setText("Añadir nuevo PDF");
        ChooseFileButton.setStyle(ColorStyle.STYLE2);
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


        NewFileButton = new BotonPersonalizado();
        NewFileButton.setText("Añadir PDF");
        NewFileButton.setStyle(ColorStyle.STYLE1);
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

        textfile= new JLabel("....");

        FilePanel.setLayout(null);

        init2.setBounds(10,10,300,20);
        ChooseFileButton.setBounds(80,30,150,20);
        textfile.setBounds(80,60,150,20);
        NewFileButton.setBounds(80,80,150,20);

        FilePanel.add(init2);
        FilePanel.add(ChooseFileButton);
        FilePanel.add(NewFileButton);
        FilePanel.add(textfile);

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