package com.fit3077.assignment2.modules;

import com.fit3077.assignment2.modules.interfaces.QRInterface;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.*;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;



public class QRService implements QRInterface {

    private static final String DS = System.getProperty("file.separator"); // file Directory Separator

    // Singleton Design: only a single instance can be active
    private static QRService qrService;
    public static QRService getInstance() {
        if (qrService == null) {
            qrService = new QRService();
        }
        return qrService;
    }


    @Override
    public String CreateQRCode(String convertToQRCode) {
        try {
            String fileName = "qrcode.png";
            int width = 100;
            int height = 100;

            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(convertToQRCode, BarcodeFormat.QR_CODE, width, height);

            // Check if server\image\ folder exists,
            // creates folder if not
            Path workingDirectory = Paths.get("").toAbsolutePath();
            File imageFolder = new File(workingDirectory+DS+"server"+DS+"assignment2"+DS+"src"+DS+"images"+DS);
            Boolean imageFolderExists = imageFolder.mkdir();
            if (!imageFolderExists) {
                System.out.println("An error occurred: \\image\\ folder could not be created");
            }

            // Write to file in \image folder
            Path filePath = (Path.of(workingDirectory+DS+"server"+DS+"assignment2"+DS+"src"+DS+"images"+DS+ Paths.get(fileName)));
            System.out.println(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

        } catch (WriterException e) {
            System.out.println("An error occurred");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String ReadQRCode(QRCode contentOfQRCode) {
        return null;
    }
}
