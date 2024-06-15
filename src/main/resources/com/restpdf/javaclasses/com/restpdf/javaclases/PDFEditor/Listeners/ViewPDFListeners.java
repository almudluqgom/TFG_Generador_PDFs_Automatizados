package com.restpdf.javaclases.PDFEditor.Listeners;

import java.util.EventListener;
public interface ViewPDFListeners extends EventListener {   //LienzoListener
    void FieldSelected(PDFEvent evt);
    void FieldAdded(PDFEvent e);
    void FieldDeleted(PDFEvent e);
    void FieldUnSelected(PDFEvent e);
}
