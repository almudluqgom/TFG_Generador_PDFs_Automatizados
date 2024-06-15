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

public class ViewPDFPanel extends JPanel {   //Lienzo2D

    private boolean iswindowmode;
    private Ellipse2D.Double ClipWindow;
    private Rectangle2D.Double clipImgFondoF;
    private Point2D pAux;   //Punto auxiliar para mantener las coordendas de donde se ha clickado
    private FieldRectangle RectAux, RecABorrar;
    ArrayList<ViewPDFListeners> PDFEventListeners = new ArrayList(); //Vector con los listeners asociados a los eventos del lienzo
    List<FieldRectangle> vRect = new ArrayList<>();
    //boolean isdeletemodeactive, cancreatenewfields;
    BufferedImage ImagenFondoFormulario;
    int selectorcounter;

    public ViewPDFPanel(BufferedImage bi) {

        iswindowmode = false;
       // cancreatenewfields = true;
        pAux = null;
        vRect = new ArrayList<>();
        //isdeletemodeactive = false;
        ClipWindow = new Ellipse2D.Double(0, 0, 100, 100);
        initComponentes();
        ImagenFondoFormulario = bi;
    }

    public BufferedImage getImagenFondoFormulario(boolean drawVector) {
        if (drawVector) {
            BufferedImage imgout = new BufferedImage(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight(), ImagenFondoFormulario.getType());
            this.paint(imgout.createGraphics());
            return imgout;
        } else {
            return this.ImagenFondoFormulario;
        }
    }
    public void setImagenFondoFormulario(BufferedImage img) {
        ImagenFondoFormulario = img;
        clipImgFondoF = new Rectangle2D.Double(0, 0, ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight());
        if (ImagenFondoFormulario != null)
            setPreferredSize(new Dimension(ImagenFondoFormulario.getWidth(), ImagenFondoFormulario.getHeight()));
    }

    public void setFieldSelected(FieldRectangle fieldSelected) {
        RectAux = fieldSelected;
      //  isdeletemodeactive = true;
    }

    public List<FieldRectangle> getvRect() {
        return vRect;
    }

    public void setvRect(List<FieldRectangle> vRect) {
        this.vRect = vRect;
    }

    public void ResetvRect() {
        vRect = new ArrayList<>();
    }

    private FieldRectangle getSelectedField(Point2D p) {
        for (int i = vRect.size() - 1; i >= 0; i = -1) {
            if (vRect.get(i).contains(p))
                return vRect.get(i);
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    private void initComponentes() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.setBackground(new Color(255, 255, 255, 0));

        addMouseMotionListener(new MouseMotionAdapter() {
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

                if (RectAux != null) {
                    pAux = new Point2D.Double(RectAux.getLocation().getX() - evt.getPoint().getX(),
                            RectAux.getLocation().getY() - evt.getPoint().getY());

                    PDFEvent pdfe = new PDFEvent(this);
                    pdfe.setpInicio(pAux); //punto de Inicio del recuadro
                    pdfe.setFieldSelected(RectAux);
                    notifyFieldSelected(pdfe);
                    //cancreatenewfields = false;
                } else {
                   // if (cancreatenewfields)
                        createRect(evt);
                }
            }

            public void mouseReleased(MouseEvent evt) {
                actDragged(evt);
                PDFEvent pdfe = new PDFEvent(this);
                pdfe.setpInicio(pAux); //punto de Inicio del recuadro
                pdfe.setFieldSelected(RectAux);
                notifyFieldAdded(pdfe);
                if ((RectAux.getRectangulo().getWidth() < 10) && (RectAux.getRectangulo().getHeight() < 10)){
                    vRect.remove(vRect.size() - 1);
                }
                updateWindowMode(evt);
            }
        });

        KeyLis listener = new KeyLis();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
    }

    public void actDragged(MouseEvent evt) {
        if (RectAux != null) {
            Point2D punto = new Point2D.Double(evt.getPoint().getX() + pAux.getX(), evt.getPoint().getY() + pAux.getY());
            RectAux.setLocation(punto);
            RectAux.updateShape(evt.getPoint());
        }
        updateWindowMode(evt);
        repaint();
    }

    public void notifyFieldSelected(PDFEvent e) {
        if (!PDFEventListeners.isEmpty()) {
            for (ViewPDFListeners listener : PDFEventListeners)
                listener.FieldSelected(e);
        }
    }
    public void notifyFieldUnSelected(PDFEvent e) {
        if (!PDFEventListeners.isEmpty()) {
            for (ViewPDFListeners listener : PDFEventListeners)
                listener.FieldUnSelected(e);
        }
    }

    public void notifyFieldAdded(PDFEvent e) {
        if (!PDFEventListeners.isEmpty()) {
            for (ViewPDFListeners listener : PDFEventListeners)
            //    if (cancreatenewfields)
                    listener.FieldAdded(e);
        }
    }
    public void notifyRemoved(PDFEvent e) {
        if (!PDFEventListeners.isEmpty()) {
            for (ViewPDFListeners listener : PDFEventListeners)
            //    if (isdeletemodeactive)
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

    public void updateFieldSelected(FieldRectangle f, boolean selected) {
        int i = vRect.indexOf(f);
        if (i != -1) {
            if (selected)
                vRect.get(i).setC(Color.green);
            else
                vRect.get(i).setC(Color.black);
            repaint(f.getRectangulo());
        }
    }
    public void addEventListener(ViewPDFListeners listener) {
        if (listener != null)
            PDFEventListeners.add(listener);
    }
    public void EnableDeleteListener(FieldRectangle fieldSelected) {
        RecABorrar = fieldSelected;
        int i = vRect.indexOf(RecABorrar);
        pAux = fieldSelected.getpAux();
        //isdeletemodeactive = true;
    }

    public void addRect(Point2D punto, FieldRectangle f) {
        f.setpAux(punto);
        vRect.add(f);
    }

    private void createRect(MouseEvent evt) {
        RectAux = new FieldRectangle(evt.getPoint());
        vRect.add(RectAux);
    }
    public void deleteLastRect(){
        RecABorrar = vRect.get(vRect.size()-1);

        PDFEvent pdfed = new PDFEvent(this);

        pdfed.setpInicio(RecABorrar.getpAux()); //punto de Inicio del recuadro
        pdfed.setFieldSelected(RecABorrar);
        pdfed.setIndex(vRect.indexOf(RecABorrar));

        vRect.remove(vRect.size()-1);
        notifyRemoved(pdfed);
    }
    public void deletefield(int index){
//        RecABorrar = vRect.get(index);
//        PDFEvent pdfed = new PDFEvent(this);
//
//        pdfed.setpInicio(RecABorrar.getpAux()); //punto de Inicio del recuadro
//        pdfed.setFieldSelected(RecABorrar);
//        pdfed.setIndex(vRect.indexOf(RecABorrar));
//        notifyRemoved(pdfed);

        vRect.remove(index);


    }
    public void setEditMode(boolean b){
//        cancreatenewfields = b;
//        isdeletemodeactive = false;
    }
    public void SelectField(int i){
        System.out.println(selectorcounter);
        PDFEvent pdfe = new PDFEvent(this);
        //primero despintamos el seleccionado
        pdfe.setFieldSelected(vRect.get(selectorcounter));
        notifyFieldUnSelected(pdfe);

        selectorcounter = i - 1;
        pAux = vRect.get(selectorcounter).getpAux();
        RectAux = vRect.get(selectorcounter);


        pdfe.setpInicio(vRect.get(selectorcounter).getpAux()); //punto de Inicio del recuadro
        pdfe.setFieldSelected(vRect.get(selectorcounter));
        notifyFieldSelected(pdfe);

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (iswindowmode)
            g2d.clip(clipImgFondoF);
        for (FieldRectangle r : vRect) {
            if ((r.getRectangulo().getWidth()) > 5 && (r.getRectangulo().getHeight() > 5))
                r.paint(g2d);
        }
    }
    private class KeyLis extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_DELETE:
                    System.out.println("Delete elemento: "+ selectorcounter);

                        if (RecABorrar != null) {
                            PDFEvent pdfed = new PDFEvent(this);

                            pdfed.setpInicio(RecABorrar.getpAux()); //punto de Inicio del recuadro
                            pdfed.setFieldSelected(RecABorrar);
                            pdfed.setIndex(vRect.indexOf(RecABorrar));
                            notifyRemoved(pdfed);
                        RecABorrar = null;
                        RectAux = null;
                        pAux = null;
                    }
                    break;

                case KeyEvent.VK_D: //debug mode

                    System.out.println(selectorcounter);
                    selectorcounter = selectorcounter + 1;
                    pAux = vRect.get(1).getpAux();
                    RectAux = vRect.get(1);

                    PDFEvent pdfe = new PDFEvent(this);
                    pdfe.setpInicio(vRect.get(1).getpAux()); //punto de Inicio del recuadro
                    pdfe.setFieldSelected(vRect.get(1));
                    notifyFieldSelected(pdfe);

        break;
        case KeyEvent.VK_E: //+1 a la figura
                    //System.out.println(selectorcounter);
                    if(selectorcounter< vRect.size()-1) {
                        PDFEvent pdfeq = new PDFEvent(this);

                        //primero despintamos el seleccionado
//                        pdfeq.setFieldSelected(vRect.get(selectorcounter));
//                        notifyFieldUnSelected(pdfeq);

                        //ahora el nuevo
                        selectorcounter = selectorcounter + 1;
                        pAux = vRect.get(selectorcounter).getpAux();
                        RectAux = vRect.get(selectorcounter);

                        pdfeq.setpInicio(vRect.get(selectorcounter).getpAux()); //punto de Inicio del recuadro
                        pdfeq.setFieldSelected(vRect.get(selectorcounter));
                        notifyFieldSelected(pdfeq);
              //          cancreatenewfields = false;
                    }

                    System.out.println(selectorcounter);
        break;
        case KeyEvent.VK_Q: //-1 a la figura
                    if(selectorcounter>0) {
                        //primero despintamos el seleccionado
                        PDFEvent pdfee = new PDFEvent(this);
//                        pdfee.setFieldSelected(vRect.get(selectorcounter));
//                        notifyFieldUnSelected(pdfee);

                        //ahora el nuevo
                        selectorcounter = selectorcounter - 1;
                        pAux = vRect.get(selectorcounter).getpAux();
                        RectAux = vRect.get(selectorcounter);


                        pdfee.setpInicio(vRect.get(selectorcounter).getpAux()); //punto de Inicio del recuadro
                        pdfee.setFieldSelected(vRect.get(selectorcounter));
                        notifyFieldSelected(pdfee);
               //         cancreatenewfields = false;
                    }

                    System.out.println(selectorcounter);
        break;
            }
        }
    }
}