package gg.scode.imageresizeservice.services.interfaces;

import gg.scode.imageresizeservice.config.exceptions.ImageWriteException;

import java.awt.image.BufferedImage;

public interface Resizer extends Resizable {
    byte[] convertToByteArray(BufferedImage data) throws Exception;
}
