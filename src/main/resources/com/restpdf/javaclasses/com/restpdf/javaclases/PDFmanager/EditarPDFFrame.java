package com.restpdf.javaclases.PDFmanager;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class EditarPDFFrame extends JFrame {

    private JPanel mainPanel;
    private JFXPanel javafxPanel;
    private WebView webComponent;
    private WebEngine engine;
    private static final String website ="https://tfgbd.000webhostapp.com/SelectPDF.php";
    public EditarPDFFrame() throws SQLException, ClassNotFoundException {

        initSwingComponents();
        loadJavaFXScene();
    }
    private void initSwingComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        JPanel butP = new JPanel();
        JButton b = new JButton("Procesar PDF");
        butP.add(b);
        JLabel nombrep = new JLabel("...");
        butP.add(nombrep);

        mainPanel.add(butP);

        javafxPanel = new JFXPanel();
        javafxPanel.setSize(500,300);
        javafxPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        mainPanel.add(javafxPanel);

        this.add(mainPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
    }
    private void loadJavaFXScene(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                BorderPane borderPane = new BorderPane();
                System.out.println("inside loadJava");
                webComponent = new WebView();

                engine = webComponent.getEngine();
                engine.load(website);

                borderPane.setCenter(webComponent);
                Scene scene = new Scene(borderPane,250,250);
                javafxPanel.setScene(scene);
            }
        });
    }
}
