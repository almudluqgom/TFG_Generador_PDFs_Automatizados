package com.restpdf.javaclases.PDFEditor.Listeners;

import java.awt.geom.Point2D;
import java.util.EventObject;
public class PDFEvent extends EventObject{  //LienzoEvent
    Point2D pInicio;
    public PDFEvent(Object source) {
        super(source);
    }

    public void setXpoint(Point2D p) {
        pInicio = p;
    }
}
