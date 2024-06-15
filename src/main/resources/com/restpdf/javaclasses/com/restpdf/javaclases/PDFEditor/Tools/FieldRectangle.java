package com.restpdf.javaclases.PDFEditor.Tools;

import java.awt.*;
import java.awt.geom.Point2D;

public class FieldRectangle {
    private Rectangle rectangulo;
    private Point2D pAux; //Punto auxiliar para mantener la coordenada de la esquina superior izquierda

      public FieldRectangle(Point2D p) {
        this.rectangulo = new Rectangle((Point) p);
        pAux = p;
    }
    public FieldRectangle(Rectangle r) {
        this.rectangulo = r;
        pAux = new Point2D.Double(r.getX(),r.getY());
    }

    public boolean contains(Point2D p) {
          return this.rectangulo.contains(p);
    }

    public void setLocation(Point2D p) {
        this.rectangulo.setFrame(p.getX(), p.getY(), rectangulo.getWidth(), rectangulo.getHeight());
    }
    public Point2D getLocation() {
        return this.rectangulo.getLocation();
    }


    public void updateShape(Point2D p) { this.rectangulo.setFrameFromDiagonal(pAux, p);
    }

    public Rectangle getRectangulo() {
        return rectangulo;
    }


    public void setpAux(Point2D pAux) {
        this.pAux = pAux;
    }

    public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.black);
        g2d.setStroke(new BasicStroke(10));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.draw(rectangulo);
    }

    public void updateColorForSelected(Graphics2D g2d, Shape f, Color c) {
        g2d.setPaint(c);
        g2d.setStroke(new BasicStroke(10));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.draw(f);
    }
}
