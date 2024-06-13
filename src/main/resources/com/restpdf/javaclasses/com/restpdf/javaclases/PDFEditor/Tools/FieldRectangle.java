package com.restpdf.javaclases.PDFEditor.Tools;

import java.awt.*;
import java.awt.geom.Point2D;

public class FieldRectangle {
    private Rectangle rectangulo;
    private Point2D pAux; //Punto auxiliar para mantener la coordenada de la esquina superior izquierda

    private Color colorf;
      public FieldRectangle(Point2D p) {
        this.rectangulo = new Rectangle((Point) p);
        pAux = p;
    }
    public FieldRectangle(Rectangle r) {
        this.rectangulo = r;
        pAux = new Point2D.Double(r.getX(),r.getY());
    }
    public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.black);
        colorf = Color.black;
        g2d.setStroke(new BasicStroke(10));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.draw(rectangulo);
    }

    public void updateColorForSelected(Graphics2D g2d, Shape f, Color c) {
       // if (colorf == Color.black){
            g2d.setPaint(c);
//            colorf = Color.GREEN;
//        }
//        else {
//            g2d.setPaint(Color.black);
//            colorf = Color.black;
//        }
        g2d.setStroke(new BasicStroke(10));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.draw(f);
    }


    public boolean contains(Point2D p) {
        return this.rectangulo.contains(p);
    }

    /**
     * Método para mover el rectángulo a una determinada posición
     *
     * @param p Posición donde debemos mover el rectángulo
     */

    public void setLocation(Point2D p) {
        this.rectangulo.setFrame(p.getX(), p.getY(), rectangulo.getWidth(), rectangulo.getHeight());
    }

    /**
     * Método para obtener la esquina superior izquierda del rectángulo
     *
     * @return Esquina superior izquierda del rectángulo
     */

    public Point2D getLocation() {
        return this.rectangulo.getLocation();
    }

    /**
     * Método para actualizar la posición de un rectángulo
     *
     * @param p Posición para la actualización
     */

    public void updateShape(Point2D p) { this.rectangulo.setFrameFromDiagonal(pAux, p);
    }

    public Rectangle getRectangulo() {
        return rectangulo;
    }

    public void setRectangulo(Rectangle rectangulo) {
        this.rectangulo = rectangulo;
    }

    public Point2D getpAux() {
        return pAux;
    }

    public void setpAux(Point2D pAux) {
        this.pAux = pAux;
    }
}
