package com.whw.qrcode.controller;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class Qrcode {
    @GetMapping(value = "getQrcode",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getQrcode() throws IOException {
        ByteArrayOutputStream stream = QRCode.from("www.baidu.com")
                .withErrorCorrection(ErrorCorrectionLevel.H)//容错度
                .withSize(256,256 )//尺寸
                .to(ImageType.PNG)
                .stream();
        byte[] bytes = stream.toByteArray();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage read = ImageIO.read(new ByteArrayInputStream(bytes));
        BufferedImage read1 = ImageIO.read(new File("warriorlogosmall.jpg"));
        BufferedImage bufferedImage = Thumbnails.of(read)
                .size(200, 200)
                .watermark(Positions.CENTER, read1, 0.0f)
                .asBufferedImage();
        ImageIO.write(bufferedImage,"jpg",byteArrayOutputStream);
        byte[] bytes1 = byteArrayOutputStream.toByteArray();
        return bytes1;
    }
}
