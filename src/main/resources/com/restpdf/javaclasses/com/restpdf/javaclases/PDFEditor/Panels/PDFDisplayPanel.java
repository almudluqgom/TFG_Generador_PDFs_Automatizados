package com.restpdf.javaclases.PDFEditor.Panels;

import javax.swing.*;
import java.io.IOException;

public class PDFDisplayPanel extends JPanel{
    JFrame f;
    public PDFDisplayPanel(String npdf){
        //--------------------------------------------------------------------------
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);

        try {
            jep.setPage(npdf);
        } catch (IOException e) {
            jep.setContentType("application/pdf");
            jep.setText("<html>Could not load</html>");
        }

        JScrollPane scrollPane = new JScrollPane(jep);
        this.add(scrollPane);
        this.setSize(600, 8000);
        f = new JFrame();
        f.getContentPane().add(scrollPane);
    }

    public JFrame getF(){
        return f;
    }
}
