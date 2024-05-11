package com.restpdf.javaclases.oldPDFmanager.PDFStructure;

import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ExtractorIMGPDF implements IEventListener {

    private List<BufferedImage> imgs = new ArrayList<>();

    @Override
    public void eventOccurred(IEventData data, EventType type) {
        if(type != EventType.RENDER_IMAGE)
            return;

        ImageRenderInfo img = (ImageRenderInfo) data;
        try {
            imgs.add(img.getImage().getBufferedImage());
        } catch (IOException e) {}
    }

    public List<BufferedImage> getImages(){
        return imgs;
    }

    @Override
    public Set<EventType> getSupportedEvents() {
        return null;
    }

}
