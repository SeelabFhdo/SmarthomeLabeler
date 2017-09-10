package de.fhdortmund.seelab.smarthomelabeler;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.hildan.fxgson.FxGson;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by jonas on 13.08.17.
 */
public class PDFCreator {

    final static float mm = 72 / 25.4f;

    public static void createPDF(File path, Smarthomeitem[] smarthomeitems) {
        System.out.println("Creating PDF");
        try (final PDDocument document = new PDDocument())
        {
            for(Smarthomeitem item : smarthomeitems) {
                final PDPage singlePage = new PDPage(new PDRectangle(62f * mm, 62f * mm));
                document.addPage(singlePage);
                final PDPageContentStream contentStream = new PDPageContentStream(document, singlePage);
                String json = FxGson.create().toJson(item);
                BufferedImage qr = createQRImage(json, 250);
                contentStream.drawImage(JPEGFactory.createFromImage(document, qr), 2*mm, 2*mm, 60*mm, 60 * mm);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.newLineAtOffset(2* mm, 2* mm);
                contentStream.showText(item.getLabel());
                contentStream.endText();
                contentStream.close();
            }
            document.save(path);
            System.out.println("Finished");

        }
        catch (IOException ioEx)
        {
            ioEx.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage createQRImage(String qrCodeText, int size) throws WriterException {
        System.out.println("Creating QR-Code");
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText,
                BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,
                BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        graphics.drawImage(image, null, 0, 0);
        System.out.println("QR-Code finished");
        return image;

    }
}
