package com.restpdf.javaclases.PDFEditor.Panels;

import com.restpdf.javaclases.PDFEditor.Listeners.*;
import com.restpdf.javaclases.PDFEditor.Tools.FieldRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ViewPDFPanel extends JPanel{   //Lienzo2D

    private boolean iswindowmode;
    private Ellipse2D.Double ClipWindow;
    private Rectangle2D.Double clipImgFondoF;
    private Point2D pAux;   //Punto auxiliar para mantener las coordendas de donde se ha clickado
    private FieldRectangle RectAux;
    ArrayList<ViewPDFListeners> PDFEventListeners = new ArrayList(); //Vector con los listeners asociados a los eventos del lienzo
    List<FieldRectangle> vRect = new ArrayList<>();
    boolean isdeletemodeactive, cancreatenewfields;
    BufferedImage ImagenFondoFormulario;
    int selectorcounter;

    public ViewPDFPanel(){
        selectorcounter = 0;
        iswindowmode = false;
        pAux = null;
        vRect = new ArrayList<>();
        isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        ImagenFondoFormulario = null;

        initComponentes();

    }
    public ViewPDFPanel(BufferedImage bi){

        iswindowmode = false;
        cancreatenewfields =true;
        pAux = null;
        vRect = new ArrayList<>();
        isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        initComponentes();
        ImagenFondoFormulario = bi;
    }

    @SuppressWarnings("unchecked")
    private void initComponentes(){
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setBackground(new Color(255, 255, 255, 0));

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent evt) {
                actDragged(evt);
            }
            public void mouseMoved(MouseEvent evt) {
                updateWindowMode(evt);
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                pAux = evt.getPoint();
                         }

            public void mousePressed(MouseEvent evt) {
                pAux = evt.getPoint();
                RectAux = getSelectedField(evt.getPoint());

                if(RectAux != null) {
                    pAux = new Point2D.Double(RectAux.getLocation().getX() - evt.getPoint().getX(),
                            RectAux.getLocation().getY() - evt.getPoint().getY());

                    PDFEvent pdfe = new PDFEvent(this);
                    pdfe.setpInicio(pAux); //punto de Inicio del recuadro
                    pdfe.setFieldSelected(RectAux);
                   // notifyPDFEvent(pAux,RectAux);
                    //cancreatenewfields = false;
                    notifyFieldSelected(pdfe);
                }else{
                    if(cancreatenewfields)
                        createRect(evt);
                }
            }
            public void mouseReleased(MouseEvent evt) {
                actDragged(evt);
                PDFEvent pdfe = new PDFEvent(this);
                pdfe.setpInicio(pAux); //punto de Inicio del recuadro
                pdfe.setFieldSelected(RectAux);
                notifyFieldAdded(pdfe);

               // notifyPDFEvent(pAux,RectAux);
                updateWindowMode(evt);
            }
        });

        KeyLis listener = new KeyLis();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
    }
    public void actDragged(MouseEvent evt){
        if(RectAux != null) {
            Point2D punto = new Point2D.Double(evt.getPoint().getX() + pAux.getX(), evt.getPoint().getY() + pAux.getY());
            RectAux.setLocation(punto);
            RectAux.updateShape(evt.getPoint());
        }
        updateWindowMode(evt);
        repaint();
    }
    public void notifyFieldSelected(PDFEvent e){
        if(!PDFEventListeners.isEmpty()) {
            for(ViewPDFListeners listener : PDFEventListeners)
                listener.FieldSelected(e);
        }
    }
    public void notifyFieldAdded(PDFEvent e){
        if(!PDFEventListeners.isEmpty()) {
            for(ViewPDFListeners listener : PDFEventListeners)
                if(cancreatenewfields)
                    listener.FieldAdded(e);
        }
    }
    public void notifyRemoved(PDFEvent e){
        if(!PDFEventListeners.isEmpty()) {
            for(ViewPDFListeners listener : PDFEventListeners)
                    listener.FieldDeleted(e);
        }
    }
    private void updateWindowMode(MouseEvent evt) {
        if (iswindowmode) {
            ClipWindow.setFrame(evt.getPoint().getX() - 100, evt.getPoint().getY() - 100, 200, 200);
        } else {
            ClipWindow.setFrame(0, 0, 0, 0);
        }
    }
    private FieldRectangle getSelectedField(Point2D p){
        for(int i = vRect.size() -1; i>=0; i = -1){
            if(vRect.get(i).contains(p))
                return vRect.get(i);
        }
        return null;
    }
    public void addEventListener(ViewPDFListeners listener){
        if(listener != null)
            PDFEventListeners.add(listener);
    }

    public void EnableDeleteListener() {
        isdeletemodeactive=true;
    }
    public void addRect(Point2D punto, FieldRectangle f){

        f.setpAux(punto);
        vRect.add(f);
    }
    private void createRect(MouseEvent evt){
        RectAux = new FieldRectangle(evt.getPoint());
        vRect.add(RectAux);
    }
     @Override
    public void paint(Graphics g){
         super.paint(g);
         Graphics2D g2d = (Graphics2D) g;
         if(iswindowmode)
             g2d.clip(clipImgFondoF);

         for (FieldRectangle r : vRect) {
             if((r.getRectangulo().getWidth()) > 10 && (r.getRectangulo().getHeight()>10))
                r.paint(g2d);
         }
     }

     public void updateFieldSelected(FieldRectangle f){
         int i = vRect.indexOf(f);
         if( i != -1) {
            vRect.get(i).updateColorForSelected((Graphics2D) ImagenFondoFormulario.getGraphics(), f.getRectangulo());
         }
     }

    public BufferedImage getImagenFondoFormulario() {
        return ImagenFondoFormulario;
    }

    public void setImagenFondoFormulario(BufferedImage img) {
        ImagenFondoFormulario = img;
        clipImgFondoF = new Rectangle2D.Double(0, 0, ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight());
        if (ImagenFondoFormulario != null)
            setPreferredSize(new Dimension(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight()));

    }

    public void setWindowMode(boolean efecto) {
        iswindowmode = efecto;
    }

    public Boolean getWindowMode() {
        return this.iswindowmode;
    }

    public List<FieldRectangle> getvRect() {
        return vRect;
    }

    public void setvRect(List<FieldRectangle> vRect) {
        this.vRect = vRect;
    }

    public ArrayList<ViewPDFListeners> getPDFEventListeners() {
        return PDFEventListeners;
    }

    public void setPDFEventListeners(ArrayList<ViewPDFListeners> PDFEventListeners) {
        this.PDFEventListeners = PDFEventListeners;
    }

    public void setFieldSelected(FieldRectangle fieldSelected) {
        RectAux = fieldSelected;
    }
    public void notifyPDFEvent(Point2D p, FieldRectangle R){
        PDFEvent pdfe = new PDFEvent(this);
        pdfe.setpInicio(p); //punto de Inicio del recuadro
        pdfe.setFieldSelected(R);
        notifyFieldSelected(pdfe);
    }
    private class KeyLis extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("mira el codigo : "+e.getExtendedKeyCode() + " y el key char: " + e.getKeyChar());
//            switch (e.getKeyCode()) {
//                case KeyEvent.VK_Q:
//                    if(selectorcounter-1>0) {              //System.out.println("VK_Q pressed");
//                        updateFieldSelected(vRect.get(selectorcounter));
//                        selectorcounter--;
//                    }
//                        PDFEvent pdfe = new PDFEvent(this);
//                        RectAux = vRect.get(selectorcounter);
//
//                        pdfe.setpInicio(RectAux.getpAux()); //punto de Inicio del recuadro
//                        pdfe.setFieldSelected(RectAux);
//                        cancreatenewfields = false;
//                        notifyFieldSelected(pdfe);
//                    repaint();
//                    break;
//
//                case KeyEvent.VK_E:
//                    if(selectorcounter+1<vRect.size()) {                        //System.out.println("VK_Q pressed");
//                        updateFieldSelected(vRect.get(selectorcounter));
//                        selectorcounter++;
//                    }
//                        PDFEvent pdfev = new PDFEvent(this);
//                        RectAux = vRect.get(selectorcounter);
//
//                        pdfev.setpInicio(RectAux.getpAux()); //punto de Inicio del recuadro
//                        pdfev.setFieldSelected(RectAux);
//                        cancreatenewfields = false;
//                        notifyFieldSelected(pdfev);
//                    repaint();
//                    break;
//                case KeyEvent.VK_DELETE:    //FUNCIONA CON EL SUPRIMIR
//                    System.out.println("delete");
//                    if(isdeletemodeactive){
//                        if(RectAux != null){
//                            PDFEvent pdfed = new PDFEvent(this);
//                            pdfed.setpInicio(RectAux.getpAux()); //punto de Inicio del recuadro
//                            pdfed.setFieldSelected(RectAux);
//                            pdfed.setIndex(vRect.indexOf(RectAux));
//
//                            vRect.remove(vRect.indexOf(RectAux));
//                            notifyRemoved(pdfed);
//                            repaint();
//                        }
//                        cancreatenewfields = true;
//                        isdeletemodeactive =false;
//                    }
//                    break;
//            }
        }
    }
}
