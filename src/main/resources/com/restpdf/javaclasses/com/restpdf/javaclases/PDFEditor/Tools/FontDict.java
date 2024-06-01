package com.restpdf.javaclases.PDFEditor.Tools;

import com.restpdf.javaclases.PDFBuilder.PDF;
import com.restpdf.javaclases.PDFBuilder.Objects.PDFDict;

public class FontDict extends PDFDict {
        public FontDict(PDF m) {
            super(m);
            dict.put("Type", "/Font");
            dict.put("Subtype", "/Type1");
            dict.put("Name", "/F1");
            dict.put("BaseFont", "/Helvetica");
            dict.put("Encoding", "/MacRomanEncoding");
        }
    }