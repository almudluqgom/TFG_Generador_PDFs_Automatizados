package com.restpdf.javaclases.PDFEditor.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class FieldLine {
    private Point2D pAux; //Punto auxiliar para mantener la coordenada de inicio
    private Line2D linea;
    String text;


    public FieldLine(Point2D pt1, Point2D pt2) {
        linea = new Line2D.Double(pt1, pt2);
        pAux=pt1;
        text="";
    }

    public Point2D getpAux() {
        return pAux;
    }

    public void setpAux(Point2D pAux) {
        this.pAux = pAux;
    }

    public Line2D getLinea() {
        return linea;
    }

    public void setLinea(Line2D linea) {
        this.linea = linea;
    }

    public boolean contains(Point2D pos) {
     boolean containsp = false;
     if (linea.ptLineDist(pos) <= 2.0)
         containsp = true;
     else{
         double difX = Math.abs(pos.getX() - linea.getX1());
         double difY = Math.abs(pos.getY() - linea.getY1());
         containsp = (difX <= 2.0 && difY <= 2.0);
     }
        return  containsp;
    }
    public int getWidth(){
        return (int) Math.round(getLinea().getX2() - getLinea().getX1());
    }
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - linea.getX1();
        double dy = pos.getY() - linea.getY1();
        Point2D newp2 = new Point2D.Double(linea.getX2() + dx, linea.getY2() + dy);
        linea.setLine(pos, newp2);
    }

    public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.black);
        g2d.setStroke(new BasicStroke(5));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.draw(linea);

    }

    public void updateColorForSelected(Graphics2D g2d, Shape f) {
        g2d.setPaint(Color.GREEN);
        g2d.setStroke(new BasicStroke(5));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g2d.draw(f);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
