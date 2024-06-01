package com.restpdf.javaclases.PDFEditor.Tools;

import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.element.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PageComponent extends Component {
    private BufferedImage bi, biFiltered;
    int w, h;
    PdfPage currentinfop;

    public PageComponent(String page, PdfPage p){
        try {
            bi = ImageIO.read(new File(page));
            w = bi.getWidth(null);
            h = bi.getHeight(null);

            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 =
                        new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFiltered = bi = bi2;
            }
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
        currentinfop = p;
    }

    public PageComponent(BufferedImage buf, PdfPage p){

            bi = buf;
            w = bi.getWidth(null);
            h = bi.getHeight(null);

            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 =
                        new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFiltered = bi = bi2;
            }
        currentinfop = p;
    }
    public PageComponent(Image page, PdfPage p){
        w =  Math.round(page.getImageWidth());
        h =  Math.round(page.getImageHeight());

        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics big = bi.getGraphics();
        big.drawImage(bi, 0, 0, null);
        biFiltered = bi;

        currentinfop = p;
    }

    public void SaveImage(String n){
        File saveFile = new File(n); //final route
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(saveFile);
        try {
        ImageIO.write(biFiltered, "jpeg", saveFile);
        } catch (IOException ex) {
            System.out.println("Cuidao, no se creó el archivo");
        }
    }

    public BufferedImage getBi() {
        return bi;
    }

    public void setBi(BufferedImage bi) {
        this.bi = bi;
    }

    public BufferedImage getBiFiltered() {
        return biFiltered;
    }

    public void setBiFiltered(BufferedImage biFiltered) {
        this.biFiltered = biFiltered;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
