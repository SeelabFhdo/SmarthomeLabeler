package de.fhdortmund.seelab.smarthomelabeler;


import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by jonas on 13.08.17.
 */
public class PDFMain
{

    public static void main (String[] args) throws IOException {
        File pdfPath = new File(System.getProperty("java.io.tmpdir") + "/smarthomeLabels.pdf");
        Smarthomeitem[] items = new Smarthomeitem[3];
        PDFCreator.createPDF(pdfPath, items);
        Desktop.getDesktop().open(pdfPath);

    }
}

