package com.restpdf.javaclases.PDFmanager;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class EditarPDFFrame extends JFrame {
    WebView webComponent;
    JPanel mainPanel;
    JFXPanel javafxPanel;

    private static final String website ="https://tfgbd.000webhostapp.com/SelectPDF.php";
    public EditarPDFFrame() throws SQLException, ClassNotFoundException {

        initSwingComponents();
        loadJavaFXScene();
    }
    private void initSwingComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        javafxPanel = new JFXPanel();
        javafxPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        mainPanel.add(javafxPanel);

        this.add(mainPanel);
//        JPanel secondPanel = new JPanel();
//        secondPanel.setLayout(new BorderLayout());
//
//        this.add(secondPanel);
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
//
//                webComponent.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>(){
//                    public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
//                        if (newState == State.SUCCEEDED){
//                            // Inject JavaScript to detect button clicks
//                            JSObject window = (JSObject) webComponent.getEngine().executeScript("window");
//                            window.setMember("javaConnector", new JavaConnector());
//                            webComponent.getEngine().executeScript(
//                                    "document.getElementById('selectpdf').addEventListener('click', function() {" +
//                                            "   javaConnector.buttonClicked();" +
//                                            "});" );
//                        }
//                    }
//                });

                webComponent.getEngine().load(website);
                borderPane.setCenter(webComponent);
                Scene scene = new Scene(borderPane,250,250);
                javafxPanel.setScene(scene);
            }
        });
    }

}
