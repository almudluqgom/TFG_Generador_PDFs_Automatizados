package com.restpdf.javaclases.PDFEditor.InternalFrames;

import com.restpdf.javaclases.bdclases.CampoF;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;
import com.restpdf.javaclases.PDFEditor.Panels.*;
import com.restpdf.javaclases.PDFEditor.Tools.*;
import com.restpdf.javaclases.bdclases.BDForms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PDFInternalFrame extends JInternalFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JScrollPane bd;
    ViewPDFPanel Panelpdf;
    String namepdf, namenewpdf;
    public ArrayList<PageComponent> pages;
    JLabel picLabel;

    BufferedImage fondoLienzo;

    public PDFInternalFrame(String npdf) {
        super("Lienzo", true, false, false, false);
        namepdf = npdf;
        namenewpdf =  npdf.replace(".pdf", "_new.pdf");

        pages= new ArrayList<>();
        initComponentes();
    }
    private void initComponentes() {

        bd = new JScrollPane();
        bd.setPreferredSize(new Dimension((int)(screenSize.getWidth() * 0.88), (int) (screenSize.getHeight() * 0.90)));
        bd.getVerticalScrollBar().setUnitIncrement(16);
        bd.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //initalization
        createPages();
        PageComponent page = pages.get(0);
                        //test display one page
                        //PageComponent page = new PageComponent("C:\\\\Users\\\\Almuchuela\\\\Downloads\\\\pagina4.jpeg");

        fondoLienzo =page.getBi();

        float w = (float) (screenSize.getWidth() * 0.85)/fondoLienzo.getWidth();

        AffineTransform at = AffineTransform.getScaleInstance(w, w);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage imgdest = atop.filter(fondoLienzo, null);

        picLabel = new JLabel(new ImageIcon(imgdest));

        Panelpdf= new ViewPDFPanel(imgdest);
        Panelpdf.setSize((picLabel.getWidth()),(picLabel.getHeight()));
        Panelpdf.add(picLabel);

        bd.setSize(picLabel.getWidth(),picLabel.getHeight());
        bd.setViewportView(Panelpdf);

        setClosable(false);
        setResizable(false);
        setIconifiable(false);
        setMaximizable(false);

        getContentPane().add(bd);
    }
    public ViewPDFPanel getPanelpdf() {
        return Panelpdf;
    }
    public BufferedImage getImagen(boolean b) {
        return this.getPanelpdf().getImagenFondoFormulario(b);
    }

//    public void setImagen(BufferedImage imgaux) {
//        this.getPanelpdf().setImagenFondoFormulario(imgaux);
//        //RESET MODE: QUITA TODO
////        this.Panelpdf.ResetvRect();
////        repaint();
////        System.out.println("reseteado");
//    }
    public void setClearBackground() {
        this.getPanelpdf().setImagenFondoFormulario(fondoLienzo);
        repaint();
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

    public void showPage(int p, List<CampoF> campos) {
        PageComponent page = pages.get(p-1);
        picLabel = new JLabel(new ImageIcon(page.getBi()));
        Panelpdf=new ViewPDFPanel(page.getBi());

       for (CampoF c : campos){
           if (c.getPage() == p){
               Point2D punto =  new Point2D.Double(c.getPosX(),c.getPosY());
               Rectangle r = new Rectangle(c.getPosX(),c.getPosY(),c.getWidth(),c.getHeight());
               FieldRectangle f = new FieldRectangle( r);
               Panelpdf.addRect(punto,f);
           }
       }
        Panelpdf.add(picLabel);
        bd.setViewportView(Panelpdf);

    }
    public void zoomPage ( List<CampoF> campos, BufferedImage img, int p, AffineTransform at){

        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage page =img;
        BufferedImage imgdest = atop.filter(page, null);
        picLabel = new JLabel(new ImageIcon(imgdest));
        Panelpdf=new ViewPDFPanel(imgdest);

        for (CampoF c : campos){
            if (c.getPage() == p){

                int posx = (int) (c.getPosX()*at.getScaleX());
                int posy = (int) (c.getPosY()*at.getScaleY());
                int nh = (int) (c.getHeight()*at.getScaleX());
                int nw = (int) (c.getWidth()*at.getScaleY());

               Point2D punto =  new Point2D.Double(posx,posy);

                Rectangle r = new Rectangle((int) punto.getX(), (int) punto.getY(),nw,nh);

                FieldRectangle f = new FieldRectangle(r);
                Panelpdf.addRect(punto,f);
                c.setPosX(posx);
                c.setPosY(posy);
                c.setHeight(nh);
                c.setWidth(nw);
            }
        }
        Panelpdf.add(picLabel);
        Panelpdf.setImagenFondoFormulario(imgdest);
        bd.setViewportView(Panelpdf);
    }

}
