package com.restpdf.javaclases.mainclases;

import com.restpdf.javaclases.bdclases.BDFrame;

import javax.swing.*;
import java.sql.SQLException;

public class MainHandler {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = null;
                try {
                    mainFrame = new MainFrame();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                mainFrame.setVisible(true);

            }
        });
    }
}
