package com.restpdf.javaclases.PDFBuilder.Objects;


import com.restpdf.javaclases.PDFBuilder.PDF;

public class OutlinesObject extends PDFDict {
    public OutlinesObject(PDF m) {
        super(m);
        dict.put("Type", "/Outlines");
        dict.put("Count", "0");
    }
}