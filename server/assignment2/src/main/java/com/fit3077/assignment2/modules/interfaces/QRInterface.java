package com.fit3077.assignment2.modules.interfaces;

import com.google.zxing.qrcode.encoder.QRCode;

public interface QRInterface {
    QRCode CreateQRCode(String convertToQRCode); // Encodes QRCode from input param String (for example, video meeting link)
    String ReadQRCode(QRCode contentOfQRCode); // Decodes QRCode from input param QRCode (for users to read the QR Code)
}
