package com.fit3077.assignment2.modules.interfaces;

import com.google.zxing.qrcode.encoder.QRCode;

public interface QRInterface {
    // Encodes QRCode from input param String (for example, video meeting link),
    // then return path to this file
    String CreateQRCode(String convertToQRCode);
    String ReadQRCode(QRCode contentOfQRCode); // Decodes QRCode from input param QRCode (for users to read the QR Code)
}
