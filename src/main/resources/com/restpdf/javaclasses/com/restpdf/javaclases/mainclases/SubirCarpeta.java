package com.restpdf.javaclases.mainclases;

import com.restpdf.javaclases.bdclases.BDForms;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SubirCarpeta  extends HttpServlet {

    private static final long serialVersionUID = 1L;
    File eml;
    String message="",nuevoNbr="",DocDestino="", DocOrigen="";
    int BUFF_SIZE = 1024;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {


        String Carpeta = request.getParameter("newfolder");
        //System.out.println(Carpeta + "= aaaaaa");

        BDForms bd = new BDForms();
        bd.setCarpeta(Carpeta);
        String carpeta = bd.getCarpeta();
        bd.close();

        if(carpeta != null) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("carpeta", carpeta);
            request.getRequestDispatcher("/volver.jsp").forward(request, response);
        }
    }

}