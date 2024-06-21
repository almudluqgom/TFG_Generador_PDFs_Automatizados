package com.restpdf.javaclases.PDFEditor.Tools;

import java.awt.*;

public enum ColorStyle {
                    // background           //foreground                        //bacgroundHover                    //BackgroundPress
    STYLE1(new Color(139, 133, 193), new Color(238, 238, 238), new Color(122, 111, 155), new Color(105, 94, 157)),
    STYLE2(new Color(203, 209, 219), new Color(58, 70, 81), new Color(81, 92, 108), new Color(230, 239, 255)),
    STYLE3(new Color(129, 94, 91), new Color(238, 238, 238), new Color(104, 81, 85), new Color(169, 155, 150));

    private ColorStyle(Color background, Color foreground, Color backgroundHover, Color backgroundPress) {
        this.background = background;
        this.foreground = foreground;
        this.backgroundHover = backgroundHover;
        this.backgroundPress = backgroundPress;
    }
    private Color background;
    private Color foreground;
    private Color backgroundHover;
    private Color backgroundPress;

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public Color getBackgroundHover() {
        return backgroundHover;
    }

    public void setBackgroundHover(Color backgroundHover) {
        this.backgroundHover = backgroundHover;
    }

    public Color getBackgroundPress() {
        return backgroundPress;
    }

    public void setBackgroundPress(Color backgroundPress) {
        this.backgroundPress = backgroundPress;
    }
}