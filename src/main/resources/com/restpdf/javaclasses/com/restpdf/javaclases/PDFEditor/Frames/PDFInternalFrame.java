package com.restpdf.javaclases.PDFEditor.Frames;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;
import com.restpdf.javaclases.PDFEditor.Panels.*;
import com.restpdf.javaclases.PDFEditor.Tools.*;
import com.restpdf.javaclases.bdclases.BDForms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PDFInternalFrame extends JInternalFrame { //VentanaInternaSM || VentanaInternaImagen
    JScrollPane bd;
    ViewPDFPanel Panelpdf;    //Lienzo2D
    String namepdf, namenewpdf;
    ArrayList<PageComponent> pages;
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
        Panelpdf = new ViewPDFPanel();

        //initalization
        createPages();
        PageComponent page = pages.get(0);
                        //test display one page
                        //PageComponent page = new PageComponent("C:\\\\Users\\\\Almuchuela\\\\Downloads\\\\pagina4.jpeg");
        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(page.getW(), page.getH()));

        JLabel picLabel = new JLabel(new ImageIcon(page.getBi()));

        //panel.add(picLabel);
        //bd = new JScrollPane(panel);
        //add(Panelpdf);
        //add(bd);

        Panelpdf.add(picLabel);
        bd = new JScrollPane(Panelpdf);
        add(bd);

        this.setClosable(false);
        this.setResizable(false);

        this.setForeground(Color.WHITE);
    }

    public JScrollPane getBd() {
        return bd;
    }

    public void setBd(JScrollPane bd) {
        this.bd = bd;
    }

    public ViewPDFPanel getPanelpdf() {
        return Panelpdf;
    }

    public void setPanelpdf(ViewPDFPanel panelpdf) {
        this.Panelpdf = panelpdf;
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
            pages.add(p);
        }
        pdf.close();

    }
    public void delete(String n){
        File myObj = new File(n);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

}
