package com.restpdf.javaclases.mainclases;

import com.restpdf.javaclases.bdclases.BDForms;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FolderUploadServlet", urlPatterns= {"/subirfichero"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,		//1mb
        maxFileSize = 1024 * 1024 * 10, 		//10mb
        maxRequestSize = 1024 * 1024 * 11		//11mb
)

public class SubirFichero extends HttpServlet {

}
