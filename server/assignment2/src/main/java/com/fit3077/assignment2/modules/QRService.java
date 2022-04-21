package com.fit3077.assignment2.modules;

import com.fit3077.assignment2.modules.interfaces.QRInterface;
import com.google.zxing.qrcode.*;
import com.google.zxing.qrcode.encoder.QRCode;

public class QRService implements QRInterface {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();


    @Override
    public QRCode CreateQRCode(String convertToQRCode) {
        QRCode qrcode = new QRCode();
        return null;
    }

    @Override
    public String ReadQRCode(QRCode contentOfQRCode) {
        return null;
    }
}
