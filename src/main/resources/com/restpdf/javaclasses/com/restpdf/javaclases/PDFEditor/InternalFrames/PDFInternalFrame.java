package com.restpdf.javaclases.PDFEditor.InternalFrames;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;
import com.restpdf.javaclases.PDFEditor.Panels.*;
import com.restpdf.javaclases.PDFEditor.Tools.*;
import com.restpdf.javaclases.bdclases.BDForms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel Panelpdf;    //Lienzo2D
    String namepdf, namenewpdf;
    public ArrayList<PageComponent> pages;
    JLabel picLabel;

    private Point2D pAux;   //Punto auxiliar para mantener las coordendas de donde se ha clickado

    public PDFInternalFrame(String npdf) {
        super(npdf, true, false, false, false);
        namepdf = npdf;
        namenewpdf =  npdf.replace(".pdf", "_new.pdf");
        pAux = null;

        pages= new ArrayList<>();
        initComponentes();

    }
    private void initComponentes() {

        bd = new JScrollPane();
        bd.getVerticalScrollBar().setUnitIncrement(16);
        //initalization
        createPages();
        PageComponent page = pages.get(0);
                        //test display one page
                        //PageComponent page = new PageComponent("C:\\\\Users\\\\Almuchuela\\\\Downloads\\\\pagina4.jpeg");
     //
        picLabel = new JLabel(new ImageIcon(page.getBi()));
        Panelpdf = new ViewPDFPanel();

        Panelpdf.add(picLabel);
        bd.setViewportView(Panelpdf);

        setClosable(false);
        setResizable(false);
        setIconifiable(false);
        setMaximizable(false);

        setForeground(Color.WHITE);
        getContentPane().add(bd);
    }

    public JLabel getPicLabel() {
        return picLabel;
    }

    public void setPicLabel(JLabel picLabel) {
        this.picLabel = picLabel;
    }

    public ViewPDFPanel getPanelpdf() {
        return Panelpdf;
    }

    public BufferedImage getImagen() {
        return this.getPanelpdf().getImagenFondoFormulario();
    }

    public void setImagen(BufferedImage imgaux) {
        this.getPanelpdf().setImagenFondoFormulario(imgaux);
    }

    public void createPages() {

        BDForms based = new BDForms();
        StringEncoder se = new StringEncoder();
        String absp = based.getCarpeta();
        absp = se.decodeFolder(absp);

        PdfDocument pdf = new PdfDocument();
        String imgpath;
        pdf.loadFromFile(namepdf);
        for (int i = 0; i < pdf.getPages().getCount(); i++) {

            BufferedImage image = pdf.saveAsImage(i, PdfImageType.Bitmap,500,500);    //Convert all pages to images and set the image Dpi
            imgpath = absp + "\\Page" + i + ".png";
            File file = new File(imgpath);

            try {
                ImageIO.write(image, "PNG", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PageComponent p = new PageComponent(imgpath);
            p.setNpagina(i);
            pages.add(p);
        }
        pdf.close();
    }
}
