package gg.scode.imageresizeservice.utils;

import gg.scode.imageresizeservice.config.TestSaveConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageWriter {

    public static BufferedImage writeBufferedImage(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    public static void saveImageFile(String fileName, BufferedImage originImage) throws IOException {
        File originImageFile = new File(TestSaveConfiguration.TEST_DIR  + "/" + fileName);
        ImageIO.write(originImage, "png", originImageFile);
    }

}
