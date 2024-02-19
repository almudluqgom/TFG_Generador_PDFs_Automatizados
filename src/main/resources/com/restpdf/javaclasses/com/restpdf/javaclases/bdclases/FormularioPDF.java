package com.restpdf.javaclases.bdclases;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
@XmlRootElement (name="formpdf")
public class FormularioPDF {
    String NamePdf,PathPDF;
    ArrayList<CampoF> Formfields;

    public String getNamePdf() {        return NamePdf;    }
    public void setNamePdf(String namePdf) {        NamePdf = namePdf;    }
    public String getPathPDF() {        return PathPDF;    }
    public void setPathPDF(String pathPDF) {        PathPDF = pathPDF;    }
    public ArrayList<CampoF> getFormfields() {        return Formfields;    }
    public void setFormfields(ArrayList<CampoF> formfields) {        Formfields = formfields;    }

    FormularioPDF(String name, String path,  ArrayList<CampoF> fields){
        setNamePdf(name);
        setFormfields(fields);
        setPathPDF(path);
    }
}
