package com.restpdf.javaclases.PDFEditor.Panels;

import com.restpdf.javaclases.PDFEditor.Tools.FieldLine;
import com.restpdf.javaclases.PDFEditor.Tools.FieldRectangle;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FillPanelPDF  extends JPanel {   //Lienzo2D

    private boolean iswindowmode;
    private Ellipse2D.Double ClipWindow;
    private Rectangle2D.Double clipImgFondoF;
    private FieldLine LineAux;
    List<FieldLine> vLines = new ArrayList<>();
    boolean isdeletemodeactive;
    BufferedImage ImagenFondoFormulario;

    public FillPanelPDF(BufferedImage bi){
        iswindowmode = false;
        vLines = new ArrayList<>();
        isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        initComponentes();
        ImagenFondoFormulario = bi;

        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.setLayout(new BorderLayout(1, 1));
        this.setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
    }

    public BufferedImage getImagenFondoFormulario(boolean drawVector) {
        if (drawVector) {
            BufferedImage imgout = new BufferedImage(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight(), ImagenFondoFormulario.getType());
            this.paint(imgout.createGraphics());
            return imgout;
        } else {
            return this.ImagenFondoFormulario;
        }
    }
    public void setImagenFondoFormulario(BufferedImage img) {
        ImagenFondoFormulario = img;
        clipImgFondoF = new Rectangle2D.Double(0, 0, ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight());
        if (ImagenFondoFormulario != null){
            setPreferredSize(new Dimension(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight()));
        }

    }

    public List<FieldLine> getvLines() {
        return vLines;
    }

    public void addnewLine(FieldLine l){
        vLines.add(l);
    }
    @SuppressWarnings("unchecked")
    private void initComponentes(){
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setFocusable(true);
        this.setLocation(this.getWidth()/2, this.getHeight()/2    );
    }
        @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if(iswindowmode)
            g2d.clip(clipImgFondoF);

        for (FieldLine r : vLines) {
            r.paint(g2d);

            if(r.getText() != null){
                g2d.setPaint(Color.blue);
                g2d.setFont(new Font("Serif", Font.BOLD, 20));

                String s = r.getText();
                int x = (int) r.getLinea().getX1();
                int y = (int) (r.getLinea().getY1()-5);
                g2d.drawString(s, x, y);
            }
        }
    }
}
