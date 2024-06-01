package com.restpdf.javaclases.PDFEditor.Tools;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.restpdf.javaclases.bdclases.BDForms;

import java.io.IOException;

public class StringEncoder {
    public String desencripta(String data) {

        //System.out.println("before desencrpta:" + data);
                if(data.contains("%20%"))
                    data = data.replace("%20", " ");
                if(data.contains("%2D"))
                    data = data.replace("%2D", "-");
                if(data.contains("%5C"))
                    data = data.replace("%5C", "\\\\");
        //System.out.println("after desencrpta:" + data);
        return data;
    }
    public String encripta(String data) {
        //System.out.println("before encrpta" + data);
        if(data.contains(" "))
            data = data.replace(" ", "%20");
        if(data.contains("-"))
            data = data.replace("-", "%2D");
        if(data.contains("\\"))
            data = data.replace("\\", "\\\\");

        //System.out.println("after encrpta" + data);
        return data;
    }
    public String decodeFolder(String data){

        data = desencripta(data);
        data = data.replace(" ", "");
        data = data.replace("[", "");
        data = data.replace("]", "");

        return data;
    }

}
