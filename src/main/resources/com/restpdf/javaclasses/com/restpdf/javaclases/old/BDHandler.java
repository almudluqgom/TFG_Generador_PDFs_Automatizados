package com.restpdf.javaclases.old;

import com.restpdf.javaclases.bdclases.BDFrame;

import javax.swing.*;
import java.sql.SQLException;

public class BDHandler{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BDFrame mainFrame = null;
                try {
                    mainFrame = new BDFrame();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                mainFrame.setVisible(true);

            }
        });
    }
}

