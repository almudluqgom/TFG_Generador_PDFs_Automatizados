package com.restpdf.javaclases.oldPDFmanager.PDFStructure;

import java.nio.ByteBuffer;
import java.util.logging.Logger;
public class old_BibliotecaPDF {

    private static final Logger logger = Logger.getLogger(old_BibliotecaPDF.class.toString());

    private old_CabeceraPDF fileHeader;
    private String fileOrigin;
    private ByteBuffer mappedFileByteBuffer;
    private final Object mappedFileByteBufferLock = new Object();
    private CatalogoPDF catalog;

    private SecurityManager securityManager;

public old_BibliotecaPDF(){}
}
