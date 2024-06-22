package com.restpdf.javaclases.PDFEditor.Tools;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.restpdf.javaclases.PDFEditor.Tools.FieldLine;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFCreator {
       List<FieldLine> newtextlines;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    PdfStamper stamp;
    String origen,destino;
    float factorm;
    Dimension dimensionp;

    public PDFCreator(String src,Dimension d){
        origen = src;
        destino = src.replace(".pdf","_new.pdf");
        dimensionp = d;
    }

    public void addNewTexts(List<FieldLine> fieldLines) {
        newtextlines = fieldLines;
    }
    public void fillPDF(){
        try {
            PdfReader reader = new PdfReader(origen);

            Rectangle dim = reader.getPageSize(reader.getPageN(1));
            Document document = new Document(dim);

            float newscale = (float) (document.getPageSize().getHeight()/ dimensionp.getHeight());
            float newscale2 = (float) (document.getPageSize().getWidth()/ dimensionp.getWidth());
            stamp = new PdfStamper(reader, new FileOutputStream(destino));
            document.open();

            for (FieldLine f : newtextlines) {
                float h1 = document.getPageSize().getHeight();

                int calculodistY = (int) (f.getpAux().getY());
                calculodistY = (int) ((int) h1-(newscale*calculodistY));

                int calculodistX = (int) (f.getpAux().getX()*newscale2);
                FixText(f.getText(),
                        calculodistX,
                        calculodistY,
                        stamp,
                        12);
            }
            stamp.close();
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se puede acceder al archivo porque está siendo usado en otro proceso");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void print(PdfContentByte pagina){

        for (FieldLine f : newtextlines) {
            Phrase frase = new Phrase(f.getText());

            ColumnText.showTextAligned(     pagina,
                                            Element.ALIGN_LEFT, frase,
                                            (float)f.getpAux().getX(),
                                            (float)f.getpAux().getY(),
                                        0);
        }
    }
    private static void FixText(String text, int x, int y,PdfStamper writer,int size) {
        try {
            PdfContentByte cb = writer.getOverContent(1);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, size);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setFactorM(float factormultiplic) {
        factorm = factormultiplic;
    }
//
//    public void print(PdfContentByte pagina){
//
//        BaseFont bf = null;
//        try {
//            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//            for (FieldLine f : newtextlines) {
//                float x = (float) f.getpAux().getX();
//                float y = (float) f.getpAux().getY();
//
//                pagina.saveState();
//                pagina.beginText();
//                pagina.moveText(x, y);
//                pagina.setFontAndSize(bf, 12);
//                pagina.showText(f.getText());
//                pagina.endText();
//                pagina.restoreState();
//
//            }
//        } catch (DocumentException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
