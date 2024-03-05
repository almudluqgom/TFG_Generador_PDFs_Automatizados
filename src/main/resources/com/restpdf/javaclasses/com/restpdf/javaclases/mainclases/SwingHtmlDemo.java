package com.restpdf.javaclases.mainclases;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingHtmlDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationFrame mainFrame = new ApplicationFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}

class ApplicationFrame extends JFrame{

    JFXPanel javafxPanel;
    WebView webComponent;
    JPanel mainPanel;
    JTextField urlField;
    JButton goButton;
    JFileChooser chooser;
    JButton ChooserButton;
    JLabel textfolder;
    String choosertitle;
    String previousurl;
    String nexturl;

    public ApplicationFrame(){
        javafxPanel = new JFXPanel();
        initSwingComponents();
        loadJavaFXScene();
    }

    private void initSwingComponents(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(javafxPanel, BorderLayout.CENTER);

        JPanel urlPanel = new JPanel(new FlowLayout());
//        urlField = new JTextField();
//        urlField.setColumns(50);
//        urlPanel.add(urlField);

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
//        urlPanel.add(goButton);

        textfolder = new JLabel();


        ChooserButton = new JButton("Selecciona directorio");
        ChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                // disable the "All files" option.
                chooser.setAcceptAllFileFilterUsed(false);

                if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(chooser.getParent())) {
//                    System.out.println("getCurrentDirectory(): "
//                            +  chooser.getCurrentDirectory());
//                    System.out.println("getSelectedFile() : "
//                            +  chooser.getSelectedFile());
                    System.out.println("You chose to open this directory: " +
                            chooser.getSelectedFile().getAbsolutePath());

                    textfolder.setText(chooser.getSelectedFile().getAbsolutePath());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        urlPanel.add(ChooserButton);
        urlPanel.add(textfolder);
        mainPanel.add(urlPanel, BorderLayout.CENTER);

        this.add(mainPanel);
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
