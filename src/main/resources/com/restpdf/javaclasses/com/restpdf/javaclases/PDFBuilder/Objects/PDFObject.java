package com.restpdf.javaclases.PDFBuilder.Objects;

import com.restpdf.javaclases.PDFBuilder.PDF;

public abstract class PDFObject extends java.lang.Object {
    /** The containing PDF file */
    protected PDF master;

    public PDFObject(PDF m) {
        master = m;
    }

    /** Write the object to the Output Writer */
    public abstract void print();

    public void startObj() {
        // Record the starting position of this Obj in the xref table
        master.addXref();

        // Print out e.g., "42 0 obj"
        master.increasecurrObj();
        master.print(master.getCurrObj());
        master.print(" 0 obj");
        master.println();
    }

    protected void endObj() {
        master.println("endobj");
    }
}
