package com.restpdf.javaclases.PDFEditor.Tools;

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
}
