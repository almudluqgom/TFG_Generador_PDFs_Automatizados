package com.restpdf.javaclases.PDFEditor.Listeners;

import com.restpdf.javaclases.PDFEditor.Tools.FieldRectangle;

import java.awt.geom.Point2D;
import java.util.EventObject;
public class PDFEvent extends EventObject{  //LienzoEvent
    Point2D pInicio;
    FieldRectangle fieldSelected;

    public PDFEvent(Object source) {
        super(source);
    }

    public Point2D getpInicio() {
        return pInicio;
    }

    public void setpInicio(Point2D pInicio) {
        this.pInicio = pInicio;
    }

    public FieldRectangle getFieldSelected() {
        return fieldSelected;
    }

    public void setFieldSelected(FieldRectangle fieldSelected) {
        this.fieldSelected = fieldSelected;
    }
}
