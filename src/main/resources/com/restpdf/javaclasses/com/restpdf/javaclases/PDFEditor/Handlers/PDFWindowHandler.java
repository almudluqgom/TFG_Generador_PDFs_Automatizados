package com.restpdf.javaclases.PDFEditor.Handlers;

import com.restpdf.javaclases.PDFEditor.InternalFrames.PDFInternalFrame;

import javax.swing.event.InternalFrameAdapter;

public class PDFWindowHandler extends InternalFrameAdapter {    //ManejadorVentanaInterna

    @Override
    public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
        PDFInternalFrame vi = (PDFInternalFrame) evt.getInternalFrame();
        //ahora si hay más elementos, los marcamos como selected/getvalue
    }
}
