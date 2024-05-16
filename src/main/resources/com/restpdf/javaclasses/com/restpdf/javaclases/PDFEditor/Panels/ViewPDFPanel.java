package com.restpdf.javaclases.PDFEditor.Panels;

import com.restpdf.javaclases.PDFEditor.Listeners.*;
import com.restpdf.javaclases.PDFEditor.Tools.FieldRectangle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ViewPDFPanel extends JPanel {   //Lienzo2D

    private boolean iswindowmode;
    Ellipse2D.Double ClipWindow;
    private Point2D pAux;   //Punto auxiliar para mantener las coordendas de donde se ha clickado
    private FieldRectangle RectAux;
    ArrayList<ViewPDFListeners> PDFEventListeners = new ArrayList(); //Vector con los listeners asociados a los eventos del lienzo
    List<FieldRectangle> vRect = new ArrayList<>();
    boolean isdeletemodeactive;

    public ViewPDFPanel(){
        initComponentes();
        iswindowmode = false;
        pAux = null;
        vRect = new ArrayList<>();
        isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);

    }
    @SuppressWarnings("unchecked")
    private void initComponentes(){
        this.setForeground(Color.WHITE);

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent evt) {
                Point2D punto = new Point2D.Double(evt.getPoint().getX() + pAux.getX(), evt.getPoint().getY() + pAux.getY());
                RectAux.setLocation(punto);
                updateWindowMode(evt);
                repaint();
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
                    pdfe.setXpoint(pAux); //punto de Inicio del recuadro
                    notifyNewField(pdfe);
                }
            }
            public void mouseReleased(MouseEvent evt) {
                Point2D punto = new Point2D.Double(evt.getPoint().getX() + pAux.getX(), evt.getPoint().getY() + pAux.getY());
                RectAux.setLocation(punto);
                updateWindowMode(evt);
            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {      }
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_DELETE && isdeletemodeactive){
                    if(RectAux != null && vRect.contains(RectAux)) {
                        vRect.remove(RectAux);
                        RectAux = null;
                        repaint();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {   }
        });
    }


    public void notifyNewField(PDFEvent e){
        if(!PDFEventListeners.isEmpty()) {
            for(ViewPDFListeners listener : PDFEventListeners)
                listener.NewFieldCreated(e);
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
        for(int count = vRect.size() -1; count>=0; count = -1){
            if(vRect.get(count).contains(p))
                return vRect.get(count);
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
     @Override
    public void paint(Graphics g){
         super.paint(g);
         //Graphics2D g2d = (Graphics2D) g;

     }
}
