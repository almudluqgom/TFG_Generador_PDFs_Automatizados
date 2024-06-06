package com.restpdf.javaclases.PDFEditor.Listeners;

import java.util.EventListener;
public interface ViewPDFListeners extends EventListener {   //LienzoListener
    public void FieldSelected(PDFEvent evt);

    void FieldAdded(PDFEvent e);
}
