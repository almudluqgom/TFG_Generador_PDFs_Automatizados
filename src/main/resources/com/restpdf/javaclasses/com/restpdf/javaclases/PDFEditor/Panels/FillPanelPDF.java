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


    public FillPanelPDF(){

        iswindowmode = false;
        vLines = new ArrayList<>();
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        ImagenFondoFormulario = null;

        initComponentes();

    }
    public FillPanelPDF(BufferedImage bi){
        iswindowmode = false;
        vLines = new ArrayList<>();
        isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        ImagenFondoFormulario = bi;

        initComponentes();
    }

    @SuppressWarnings("unchecked")
    private void initComponentes(){
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setBackground(new Color(255, 255, 255, 0));

        this.setFocusable(true);
        this.requestFocus();
    }
    private void updateWindowMode(MouseEvent evt) {
        if (iswindowmode) {
            ClipWindow.setFrame(evt.getPoint().getX() - 100, evt.getPoint().getY() - 100, 200, 200);
        } else {
            ClipWindow.setFrame(0, 0, 0, 0);
        }
    }
    private FieldLine getSelectedField(Point2D p){
        for(int i = vLines.size() -1; i>=0; i = -1){
            if(vLines.get(i).contains(p))
                return vLines.get(i);
        }
        return null;
    }

    private void createRect(MouseEvent evt){
        LineAux = new FieldLine(evt.getPoint());
        vLines.add(LineAux);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if(iswindowmode)
            g2d.clip(clipImgFondoF);

        if (ImagenFondoFormulario != null) {
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(0, 0, ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight());
            g2d.clip(ClipWindow);
            g2d.drawImage(ImagenFondoFormulario, 0, 0, this);
        }

        for (FieldLine r : vLines) {
            r.paint(g2d);

            if(r.getText() != null){
                g2d.setPaint(Color.blue);
                g2d.setFont(new Font("Serif", Font.BOLD, 20));

                String s = r.getText();
                FontMetrics fm = g2d.getFontMetrics();
                int width = r.getWidth();
                int x = (int) r.getLinea().getX1();
                int y = (int) (r.getLinea().getY1()-5);
                g2d.drawString(s, x, y);
            }
        }
    }

    public void updateFieldSelected(FieldRectangle f){
        int i = vLines.indexOf(f);
        if( i != -1) {
            vLines.get(i).updateColorForSelected((Graphics2D) ImagenFondoFormulario.getGraphics(), f.getRectangulo());
        }
    }

    public BufferedImage getImagenFondoFormulario() {
        return ImagenFondoFormulario;
    }

    public void setImagenFondoFormulario(BufferedImage img) {
        ImagenFondoFormulario = img;
        clipImgFondoF = new Rectangle2D.Double(0, 0, ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight());
        if (ImagenFondoFormulario != null){
            setPreferredSize(new Dimension(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight()));
        }

    }

    public void setWindowMode(boolean efecto) {
        iswindowmode = efecto;
    }

    public Boolean getWindowMode() {
        return this.iswindowmode;
    }

    public List<FieldLine> getvLines() {
        return vLines;
    }

    public void setvLines(List<FieldLine> vLines) {
        this.vLines = vLines;
    }
    public void addnewLine(FieldLine l){
        vLines.add(l);
    }
}
