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
    boolean isdeletemodeactive;
    BufferedImage ImagenFondoFormulario;


    public ViewPDFPanel(){

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
        pAux = null;
        vRect = new ArrayList<>();
        isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        ImagenFondoFormulario = bi;

        initComponentes();
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
                //System.out.println( "Clicaste en " + pAux.getX() +"-" + pAux.getY());
            }

            public void mousePressed(MouseEvent evt) {
                pAux = evt.getPoint(); // System.out.println( "MousePressed situó pAu en: " + pAux.getX() +"-"+ pAux.getY());

                RectAux = getSelectedField(evt.getPoint());

                if(RectAux != null) {
                    pAux = new Point2D.Double(RectAux.getLocation().getX() - evt.getPoint().getX(),
                            RectAux.getLocation().getY() - evt.getPoint().getY());

                    PDFEvent pdfe = new PDFEvent(this);
                    pdfe.setpInicio(pAux); //punto de Inicio del recuadro
                    pdfe.setFieldSelected(RectAux);

                    notifyFieldSelected(pdfe);
                }else{
                    createRect(evt);
                }
            }
            public void mouseReleased(MouseEvent evt) {
                actDragged(evt);

                PDFEvent pdfe = new PDFEvent(this);
                pdfe.setpInicio(pAux); //punto de Inicio del recuadro
                pdfe.setFieldSelected(RectAux);
                notifyFieldAdded(pdfe);
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
                listener.FieldAdded(e);
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

         if (ImagenFondoFormulario != null) {
             g2d.setStroke(new BasicStroke(3));
             g2d.drawRect(0, 0, ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight());
             g2d.clip(ClipWindow);
             g2d.drawImage(ImagenFondoFormulario, 0, 0, this);
         }

         for (FieldRectangle r : vRect) {
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
        if (ImagenFondoFormulario != null){
            setPreferredSize(new Dimension(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight()));
        }

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

    private class KeyLis extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("mira el codigo : "+e.getExtendedKeyCode() + " y el key char: " + e.getKeyChar());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    System.out.println("VK_LEFT pressed");
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("VK_RIGHT pressed");
                    break;
                case KeyEvent.VK_DELETE:    //FUNCIONA CON EL SUPRIMIR
                    System.out.println("delete");
                    break;
            }
        }
    }
}
