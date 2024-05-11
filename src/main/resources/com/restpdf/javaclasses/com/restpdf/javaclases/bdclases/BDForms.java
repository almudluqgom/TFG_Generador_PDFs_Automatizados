package com.restpdf.javaclases.bdclases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BDForms {
    ArrayList<String> output;
    BufferedReader br;
    URLConnection urlc;

    public BDForms() {
    }

    public void close() throws IOException {
        br.close();
    }

    public void setCarpeta(String Carpeta) {
        URL url = null;
        try {
            url = new URL("https://tfgbd.000webhostapp.com/uploadNewFolder.php?valor=" + Carpeta);
            urlc = url.openConnection();
            urlc.connect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNuevoPDF(FormularioPDF NewPDF) {

        ArrayList<CampoF> fields = NewPDF.getFormfields();

        for (CampoF campoF : fields) {
            CampoF field = new CampoF(campoF);
        }
    }

    public String getCarpeta() {
        URL url = null;

        try {
            url = new URL("https://tfgbd.000webhostapp.com/selectCarpeta.php");
            urlc = url.openConnection();
            urlc.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String str = br.readLine();
            output = new ArrayList<>(Arrays.asList(str.split("<br>")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.valueOf(output);
    }

    public void setNuevoCampoPDF(String Campof, String NPDF, int p, int posx, int posy, int l, int a) throws SQLException {
        URL url = null;

        try {
            url = new URL("https://tfgbd.000webhostapp.com/AddCampoAlPDF.php?cp=" + Campof + "?np=" + NPDF + "pag" + p + "?posx=" + posx + "?posy=" + posy + "?l=" + l + "?a=" + a);
            urlc = url.openConnection();
            urlc.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
    }
}
