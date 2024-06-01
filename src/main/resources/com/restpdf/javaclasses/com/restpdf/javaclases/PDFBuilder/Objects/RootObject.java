package com.restpdf.javaclases.PDFBuilder.Objects;

import com.restpdf.javaclases.PDFBuilder.PDF;

public class RootObject extends PDFDict {
    public RootObject(PDF m) {
        super(m);
        dict.put("Type", "/Catalog");
        dict.put("Outlines", "3 0 R");
        dict.put("Pages", "4 0 R");
    }
}