package com.restpdf.javaclases.PDFBuilder.Objects;

import com.restpdf.javaclases.PDFBuilder.PDF;

public class InfoObject extends PDFDict {
    public InfoObject(PDF m) {
        super(m);
        dict.put("Title", "(Sample PDF by SPDF)");
        dict.put("Creator", "(Darwin Open Systems SPDF Software)");
        dict.put("Created", "(D:20000516010203)");
    }
}