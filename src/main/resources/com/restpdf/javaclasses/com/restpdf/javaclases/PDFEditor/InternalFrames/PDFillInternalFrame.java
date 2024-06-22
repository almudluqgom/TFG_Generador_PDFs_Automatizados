package com.restpdf.javaclases.PDFEditor.InternalFrames;

import com.restpdf.javaclases.PDFEditor.Panels.FillPanelPDF;
import com.restpdf.javaclases.PDFEditor.Panels.ViewPDFPanel;
import com.restpdf.javaclases.PDFEditor.Tools.FieldLine;
import com.restpdf.javaclases.PDFEditor.Tools.FieldRectangle;
import com.restpdf.javaclases.PDFEditor.Tools.PageComponent;
import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;
import com.restpdf.javaclases.bdclases.BDForms;
import com.restpdf.javaclases.bdclases.CampoF;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;

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

public class PDFillInternalFrame extends JInternalFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JScrollPane bd;
    FillPanelPDF Panelpdf;    //Lienzo2D
    String namepdf, namenewpdf;
    public ArrayList<PageComponent> pages;
    JLabel picLabel;
    BufferedImage fondoLienzo;

    float factormultiplic;

    public PDFillInternalFrame(String npdf) {
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
                    //
        fondoLienzo =page.getBi();

        factormultiplic = (float) (screenSize.getWidth() * 0.85)/fondoLienzo.getWidth();

        AffineTransform at = AffineTransform.getScaleInstance(factormultiplic, factormultiplic);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage imgdest = atop.filter(fondoLienzo, null);

        picLabel = new JLabel(new ImageIcon(imgdest));

        Panelpdf = new FillPanelPDF(imgdest);
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

    public float getFactormultiplic() {
        return factormultiplic;
    }

    public void setFactormultiplic(float factormultiplic) {
        this.factormultiplic = factormultiplic;
    }

    public FillPanelPDF getPanelpdf() {
        return Panelpdf;
    }

    public BufferedImage getImagen(boolean b) {
        return this.getPanelpdf().getImagenFondoFormulario(b);
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

    public void showPage(int p, java.util.List<CampoF> campos) {
        PageComponent page = pages.get(p-1);
        picLabel = new JLabel(new ImageIcon(page.getBi()));
        Panelpdf=new FillPanelPDF(page.getBi());

        for (CampoF c : campos){
            if (c.getPage() == p){
                Point2D punto =  new Point2D.Double(c.getPosX(),c.getPosY());
                Point2D punto1 =  new Point2D.Double(c.getPosX()+ c.getWidth(),c.getPosY());
                FieldLine f = new FieldLine(punto,punto1);
                Panelpdf.addnewLine(f);
            }
        }
        Panelpdf.add(picLabel);
        bd.setViewportView(Panelpdf);

    }
    public void zoomPage (List<CampoF> campos, BufferedImage img, int p, AffineTransform at){

        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage page =img;
        BufferedImage imgdest = atop.filter(page, null);
        picLabel = new JLabel(new ImageIcon(imgdest));

        List<FieldLine> L = Panelpdf.getvLines();
        Panelpdf=new FillPanelPDF(imgdest);

        for (CampoF c : campos){
            if (c.getPage() == p){

                int posx = (int) (c.getPosX()*at.getScaleX());
                int posy = (int) (c.getPosY()*at.getScaleY());
                int nw = (int) (c.getWidth()*at.getScaleY());
                int nh = (int) (c.getHeight()*at.getScaleX());
                String t = L.get(campos.indexOf(c)).getText();

                Point2D punto =  new Point2D.Double(posx,posy+nh);
                Point2D punto1 =  new Point2D.Double(posx+ nw,posy+nh);
                FieldLine f = new FieldLine(punto,punto1);
                f.setText(t);
                Panelpdf.addnewLine(f);

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
