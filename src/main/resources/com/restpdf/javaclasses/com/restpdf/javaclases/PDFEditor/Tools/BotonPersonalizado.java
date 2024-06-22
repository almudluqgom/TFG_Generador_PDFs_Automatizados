package com.restpdf.javaclases.PDFEditor.Tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;


public class BotonPersonalizado extends JButton {
    private ColorStyle style = ColorStyle.STYLE1;
    private ButtonColor currentStyle = new ButtonColor(ColorStyle.STYLE1);
    private int round = 3;

    public BotonPersonalizado() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(6, 6, 6, 6));
        setForeground(Color.WHITE);
    }

    public void setStyle(ColorStyle style) {
        if (this.style != style) {
            this.style = style;
            currentStyle.changeStyle(style);
            setForeground(style.getForeground());
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = 0;
        int y = 0;
        int width = getWidth();
        int height = getHeight();
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, round, round));
        g2.setColor(currentStyle.background);
        g2.fill(area);
        area.subtract(new Area(new RoundRectangle2D.Double(x, y, width, height - 2, round, round)));
        g2.setColor(currentStyle.backgroundHover);
        g2.fill(area);
        g2.dispose();
        super.paintComponent(grphcs);
    }



    protected class ButtonColor {

        public ButtonColor(ColorStyle style) {
            changeStyle(style);
        }


        private Color background;
        private Color backgroundHover;

        private void changeStyle(ColorStyle style) {
            this.background = style.getBackground();
            this.backgroundHover = style.getBackground();
        }
    }
}
