package com.restpdf.javaclases.mainclases;

import com.restpdf.javaclases.bdclases.BDForms;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SwingHtmlDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationFrame mainFrame = null;
                try {
                    mainFrame = new ApplicationFrame();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                mainFrame.setVisible(true);

            }
        });
    }
}

class ApplicationFrame extends JFrame{

    BDForms bd;
    JFXPanel javafxPanel;
    WebView webComponent;
    JPanel mainPanel,
           FolderPanel;
    JButton ChooserButton;
    JFileChooser chooser;
    JSeparator s;
    JLabel textfolder;
    static String choosertitle = "Selecciona la carpeta: ";
    public ApplicationFrame() throws SQLException, ClassNotFoundException {
        bd = new BDForms();
        javafxPanel = new JFXPanel();
        initSwingComponents();
        loadJavaFXScene();
    }

    private void initSwingComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


//        s = new JSeparator();//FIN DE SECCION
//        s.setOrientation(SwingConstants.HORIZONTAL);

//        JPanel urlPanel = new JPanel(new FlowLayout());
//        urlField = new JTextField();
//        urlField.setColumns(50);
//        urlPanel.add(urlField);

        FolderPanel = new JPanel(new FlowLayout());
        textfolder = new JLabel();
        ChooserButton = new JButton("Cargar directorio");
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
//
        FolderPanel.add(ChooserButton);
        FolderPanel.add(textfolder);
        mainPanel.add(FolderPanel, BorderLayout.NORTH);
        //FolderPanel.add(s);

//        SearchPanel = new JPanel();
//        NewFolderButton = new JButton("Selecciona directorio");

//        goButton = new JButton("Go");
//        goButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        String url = urlField.getText();
//                        if ( url != null && url.length() > 0){
//                            webComponent.getEngine().load(url);
//                        }
//                    }
//                });
//
//            }
//        });
//        FolderPanel.add(goButton);
//        FolderPanel = new JPanel(new FlowLayout());
//        FolderPanel.add(s);
//
//        mainPanel.add(FolderPanel, BorderLayout.NORTH);
//
//        SearchPanel = new JPanel(new FlowLayout());
//        NewFolderButton.addActionListener(new ActionListener() { //confirmación de directorio como almacen. pdf
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String f = textfolder.getText();
//
//                try {
//                  bd.setCarpeta(f);
//                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
//                }
//                System.out.println("Exito actualizando la base de datos");
//                JOptionPane.showMessageDialog(null, "Exito actualizando la base de datos",
//                        "InfoBox: Carpeta de trabajo actualizada", JOptionPane.INFORMATION_MESSAGE);
//
//            }
//        });
//        SearchPanel.add(NewFolderButton);
//        SearchPanel.add(s);
//
//
//
//        mainPanel.add(SearchPanel, BorderLayout.CENTER);
        mainPanel.add(javafxPanel, BorderLayout.CENTER); //panel donde se muestra la conexión url
        this.add(mainPanel);
//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,600);
    }

    private void loadJavaFXScene(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                BorderPane borderPane = new BorderPane();
                System.out.println("inside loadJava");
                webComponent = new WebView();

                //webComponent.getEngine().load("http://localhost:8080/ProyectoRest2/");

                borderPane.setCenter(webComponent);
                Scene scene = new Scene(borderPane,450,450);
                javafxPanel.setScene(scene);
            }
        });
    }
}
