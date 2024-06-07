package com.restpdf.javaclases.PDFEditor.Tools;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.restpdf.javaclases.bdclases.BDForms;
import com.restpdf.javaclases.bdclases.CampoF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    public CampoF transformaStringEnCampo(String campo) {

        ArrayList<String> c = new ArrayList<>(Arrays.asList(campo.split("<b>")));
        CampoF finaldata = new CampoF(   c.get(0),
                                         c.get(1),
                        Integer.parseInt(c.get(2)),
                        Integer.parseInt(c.get(3)),
                        Integer.parseInt(c.get(4)),
                        Integer.parseInt(c.get(5)),
                        Integer.parseInt(c.get(6))     );
        return finaldata;
    }
}
