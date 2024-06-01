package com.restpdf.javaclases.PDFBuilder.Objects;

import com.restpdf.javaclases.PDFBuilder.Objects.PDFObject;
import com.restpdf.javaclases.PDFBuilder.PDF;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class PDFDict extends PDFObject {
    /** The Dictionary is a HashTable. Put the keys without a
     * leading slash, since they always have it. Values can
     * be /names, (strings), or whatever.
     */
    protected Hashtable dict;

    public PDFDict(PDF m) {
        super(m);
        dict = new Hashtable();
    }

    public void print() {
        startObj();
        printDict();
        endObj();
    }

    public void startObj() {
        // Record the starting position of this Obj in the xref table
        master.addXref();

        // Print out e.g., "42 0 obj"
        master.increasecurrObj();
        master.print(master.getCurrObj());
        //master.print(master.currObj++);
        master.print(" 0 obj");
        master.println();
    }

    protected void endObj() {
        master.println("endobj");
    }

    protected void printDict() {
        master.println("<<");
        Enumeration e = dict.keys();
        while (e.hasMoreElements()) {
            master.print("\t/");
            String key = (String)e.nextElement();
            master.print(key);
            master.print(" ");
            master.print(dict.get(key));
            master.println();
        }
        master.println(">>");
    }

    public Hashtable getDict() {
        return dict;
    }

    public void setDict(Hashtable dict) {
        this.dict = dict;
    }

    public void setAuthor(String au){
        dict.put("Author", "(" + au + ")");
    }
}