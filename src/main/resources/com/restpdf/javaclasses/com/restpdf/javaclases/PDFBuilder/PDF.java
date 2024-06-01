package com.restpdf.javaclases.PDFBuilder;

import com.restpdf.javaclases.PDFBuilder.Objects.*;
import com.restpdf.javaclases.PDFEditor.Tools.FontDict;

import java.io.*;
import java.text.*;
import java.util.*;

public class PDF {
    /** The output writer */
    protected PrintWriter out;

    /** The list of pages */
    protected ArrayList pages;

    /** The list of object xrefs */
    protected ArrayList xrefs;

    /** The root object */
    PDFObject rootObj = new RootObject(this);

    /** The Info object */
    InfoObject infoObj = new InfoObject(this);

    /** The outlines (outline font) object */
    OutlinesObject outlinesObj = new OutlinesObject(this);

    /** The Pages object */
    PagesObject pagesObj = new PagesObject(this);

    /** The Font Dictionary */
    FontDict fontDict = new FontDict(this);

    /** The object number of the current object */
    protected int currObj = 1;

    /** A flag to avoid writing twice */
    protected boolean startedWriting = false;

    /** A magic number that identifies the output as a PDF file */
    protected final static String PDF_MAGIC = "%PDF-1.0";

    /** Constructor */
    public PDF(PrintWriter o) {
        out = o;

        pages = new ArrayList();
        xrefs = new ArrayList();

    }

    public void add(Page p) {
        pages.add(p);
    }

    public void insertPage(int where, Page p) {
        pages.add(where, p);
    }

    // OUTPUT METHODS -- we provide our own print/println, so we
    // can keep track of file offsets (it was either that, or kludgily
    // use a RandomAccessFile and the getFilePointer() method).

    long offset = 0;

    /** Print a String */
    protected void print(String s){
        out.print(s);
        offset += s.length();
    }

    /** Println a String */
    public void println(String s) {
        print(s);
        print("\n");
    }

    /** Print an Object */
    public void print(Object o) {
        print(o.toString());
    }

    /** Println an Object */
    protected void println(Object o) {
        println(o.toString());
    }

    /** Print an int */
    protected void print(int i) {
        String s = Integer.toString(i);
        print(s);
    }

    /** Println an int */
    protected void println(int i) {
        String s = Integer.toString(i);
        print(s);
    }

    /** Println with no args */
    public void println() {
        print("\n");
    }

    // END OF OUTPUT METHODS

    /** Add an entry into the offset table */
    public void addXref() {
        xrefs.add(new Long(offset));
    }

    /** Write the entire output */
    public void writePDF() {
        if (startedWriting) {
            throw new IllegalStateException(
                    "writePDF() can only be called once.");
        }
        startedWriting = true;

        writePDFHeader();
        writePDFbody();
        writeXrefs();
        writePDFTrailer();
        out.flush();
        out.close();
    }

    protected void writePDFHeader() {
        println(PDF_MAGIC);

        rootObj.print();  // 1

        infoObj.print();  // 2

        outlinesObj.print(); // 3

        pagesObj.print();  // 4
    }

    protected void writePDFbody() {

        for (int i=0; i<pages.size(); i++) {
            ((Page)pages.get(i)).print();    // 5, 6
        }

        addXref();
        print(currObj++); println(" 0 obj");
        println("[/PDF /Text]");
        println("endobj");

        fontDict.print();    // 8
    }

    DecimalFormat nf10 = new DecimalFormat("0000000000");
    DecimalFormat nf5 = new DecimalFormat("00000");

    /** Write one Xref, in the format 0000000000 65535 f */
    protected void printXref(long n, int where, char inUse) {
        println(nf10.format(n) + " " +  nf5.format(where) + " " + inUse);
    }

    long xrefStart;

    /** Write all the xrefs, using the format above */
    protected void writeXrefs() {
        xrefStart = offset;
        println("xref");
        print(0);
        print(" ");
        print(xrefs.size() + 1);
        println();
        // "fake" entry at 0, always 0, -1, f(free).
        printXref(0, 65535, 'f');
        // Remaining xref entries are for real objects.
        for (int i=0; i<xrefs.size(); i++) {
            Long lo = (Long)xrefs.get(i);
            long l = lo.longValue();
            printXref(l, 0, 'n');
        }

    }

    protected void writePDFTrailer() {
        println("trailer");
        println("<<");
        println("/Size " + (xrefs.size() + 1));
        println("/Root 1 0 R");
        println("/Info 2 0 R");
        println(">>");
        println("% startxref");
        println("% " + xrefStart);
        println("%%EOF");
    }
    public void setAuthor(String au) {
        infoObj.setAuthor(au);
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public ArrayList getPages() {
        return pages;
    }

    public void setPages(ArrayList pages) {
        this.pages = pages;
    }

    public ArrayList getXrefs() {
        return xrefs;
    }

    public void setXrefs(ArrayList xrefs) {
        this.xrefs = xrefs;
    }

    public PDFObject getRootObj() {
        return rootObj;
    }

    public void setRootObj(PDFObject rootObj) {
        this.rootObj = rootObj;
    }

    public InfoObject getInfoObj() {
        return infoObj;
    }

    public void setInfoObj(InfoObject infoObj) {
        this.infoObj = infoObj;
    }

    public OutlinesObject getOutlinesObj() {
        return outlinesObj;
    }

    public void setOutlinesObj(OutlinesObject outlinesObj) {
        this.outlinesObj = outlinesObj;
    }

    public PagesObject getPagesObj() {
        return pagesObj;
    }

    public void setPagesObj(PagesObject pagesObj) {
        this.pagesObj = pagesObj;
    }

    public FontDict getFontDict() {
        return fontDict;
    }

    public void setFontDict(FontDict fontDict) {
        this.fontDict = fontDict;
    }

    public int getCurrObj() {
        return currObj;
    }

    public void setCurrObj(int currObj) {
        this.currObj = currObj;
    }

    public boolean isStartedWriting() {
        return startedWriting;
    }

    public void setStartedWriting(boolean startedWriting) {
        this.startedWriting = startedWriting;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public DecimalFormat getNf10() {
        return nf10;
    }

    public void setNf10(DecimalFormat nf10) {
        this.nf10 = nf10;
    }

    public DecimalFormat getNf5() {
        return nf5;
    }

    public void setNf5(DecimalFormat nf5) {
        this.nf5 = nf5;
    }

    public long getXrefStart() {
        return xrefStart;
    }

    public void setXrefStart(long xrefStart) {
        this.xrefStart = xrefStart;
    }

    public void increasecurrObj() {
        currObj++;
    }
}
