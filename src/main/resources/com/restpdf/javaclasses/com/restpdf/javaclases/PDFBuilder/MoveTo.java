package com.restpdf.javaclases.PDFBuilder;

import com.restpdf.javaclases.PDFBuilder.Objects.PDFObject;

public class MoveTo extends PDFObject {
    protected int x, y;

    public MoveTo(PDF m, int x, int y) {
        super(m);
        this.x = x;
        this.y = y;
    }

    public void print() {
        throw new IllegalStateException("print() called on a Text obj");
    }

    public void print(StringBuffer sb) {
        sb.append(x);
        sb.append(' ');
        sb.append(y);
        sb.append(" Td\n");
    }
}