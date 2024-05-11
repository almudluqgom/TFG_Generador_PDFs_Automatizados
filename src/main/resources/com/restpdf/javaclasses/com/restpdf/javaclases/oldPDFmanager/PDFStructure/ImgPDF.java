package com.restpdf.javaclases.oldPDFmanager.PDFStructure;



import java.awt.image.BufferedImage;

public class ImgPDF {
    String imgSrc;
    BufferedImage img;
    int x,y; //position in the page


    public ImgPDF(){
        imgSrc=null;        img =null;
        x=0;        y=0;
    }

    public ImgPDF(String url,BufferedImage Bimg ,int posx, int posy){
        imgSrc=url;
        img =Bimg;
        x=posx;
        y=posy;
    }

}
