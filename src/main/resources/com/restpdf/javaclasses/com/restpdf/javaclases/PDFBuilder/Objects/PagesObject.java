package com.restpdf.javaclases.PDFBuilder.Objects;


import com.restpdf.javaclases.PDFBuilder.PDF;

public class PagesObject extends PDFDict {
    public PagesObject(PDF m) {
        super(m);
        dict.put("Type", "/Pages");
        dict.put("Count", "1");
        dict.put("Kids", "[5 0 R]");
    }
}