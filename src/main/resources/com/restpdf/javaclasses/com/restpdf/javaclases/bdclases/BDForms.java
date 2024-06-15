package com.restpdf.javaclases.bdclases;

import com.restpdf.javaclases.PDFEditor.Tools.StringEncoder;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BDForms {
    ArrayList<String> output;
    BufferedReader br;
    URLConnection urlc;

    public String getCarpeta() {
        URL url = null;

        try {
            url = new URL("https://tfgbd.000webhostapp.com/selectCarpeta.php");
            urlc = url.openConnection();
            urlc.connect();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error intentando abrir el documento ", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);

        }

        try {
            br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String str = br.readLine();
            output = new ArrayList<>(Arrays.asList(str.split("<br>")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String o = String.valueOf(output);
        o = o.replace(" ", "");
        o = o.replace("[", "");
        o = o.replace("]", "");

        return o;
    }

    public String setNuevoCampoPDF(CampoF newf) throws SQLException {

        try {
            String f = newf.getNameFatherForm();
            StringEncoder e = new StringEncoder();
            f= e.encripta(f);

            String data = "&" + URLEncoder.encode("CamposFormularios", "UTF-8") + "=" + URLEncoder.encode(newf.getNameField(), "UTF-8");
            data += "&" + URLEncoder.encode("NombreFormulario", "UTF-8") + "=" + URLEncoder.encode(f, "UTF-8");
            data += "&" + URLEncoder.encode("Pagina", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(newf.getPage()), "UTF-8");
            data += "&" + URLEncoder.encode("PosicionX", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(newf.getPosX() ), "UTF-8");
            data += "&" + URLEncoder.encode("PosicionY", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(newf.getPosY() ), "UTF-8");
            data += "&" + URLEncoder.encode("Largo", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(newf.getWidth()), "UTF-8");
            data += "&" + URLEncoder.encode("Ancho", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(newf.getHeight()), "UTF-8");
            //System.out.println(data);
            String urlString = "https://tfgbd.000webhostapp.com/AddCampoAlPDF.php" + "?" + data;
            System.out.println(urlString);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                System.out.println("inside of readline: "+line);
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
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

    public String setCarpeta(String Carpeta) {
        URL url = null;
        String result = null;
        try {
            StringEncoder e = new StringEncoder();
            Carpeta= e.encripta(Carpeta);

            String u = "https://tfgbd.000webhostapp.com/uploadNewFolder.php?valor=" + Carpeta;

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
