package com.restpdf.javaclases.bdclases;

import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
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

    public String sendNewFile(String f) {
        URL url = null;
        String result = null;
        try {
            StringEncoder e = new StringEncoder();
            f= e.encripta(f);

            String u = "https://tfgbd.000webhostapp.com/uploadNewPDF.php?valor=" + f;

            url = new URL(u);
            urlc = url.openConnection();
            urlc.connect();

            br = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(u)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
            String str = br.readLine();
            System.out.println(str);
            result = str;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
