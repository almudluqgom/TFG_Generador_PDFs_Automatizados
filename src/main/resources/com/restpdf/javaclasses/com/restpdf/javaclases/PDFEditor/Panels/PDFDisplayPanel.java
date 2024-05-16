package com.restpdf.javaclases.PDFEditor.Panels;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.IOException;



   public class PDFDisplayPanel extends JPanel{
    JFrame f;
    public PDFDisplayPanel(String npdf){


        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);

        try {
            jep.setPage("file:///C:/Users/Almuchuela/Downloads/Relato%20Crimson.pdf");
        } catch (IOException e) {
            jep.setContentType("text/html");
            jep.setText("<html>Could not load</html>");
        }

        //jep.setEditorKit(new DefaultEditorKit());
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
