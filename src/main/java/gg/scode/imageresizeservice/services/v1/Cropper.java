package gg.scode.imageresizeservice.services.v1;

import gg.scode.imageresizeservice.config.exceptions.AbnormalUriFormatException;
import gg.scode.imageresizeservice.config.exceptions.ImageWriteException;
import gg.scode.imageresizeservice.config.exceptions.NoImageFromURI;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.services.interfaces.Resizer;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Service
public class Cropper implements Resizer {

    @Override
    public byte[] resize(ImageStandard imageStandard) throws Exception {

        Thumbnails.Builder<URL> thBuilder = Thumbnails.of(getImageUrl(imageStandard.getUrl()));

        if ( imageStandard.getWidth() == null ) {
            thBuilder.height(imageStandard.getHeight());
        } else if ( imageStandard.getHeight() == null ) {
            thBuilder.width(imageStandard.getWidth());
        } else {
            thBuilder.size(imageStandard.getWidth(),imageStandard.getHeight())
                    .keepAspectRatio(imageStandard.getKeepRatio());
        }

        return convertToByteArray(
                thBuilder.asBufferedImage()
        );
    }

    private boolean isKeepRatio(ImageStandard imageStandard) {
        return imageStandard.getWidth() == null || imageStandard.getHeight() == null;
    }

    private URL getImageUrl(String imageUri) throws MalformedURLException {
        return URI.create(imageUri).toURL();
    }

    @Override
    public byte[] convertToByteArray(BufferedImage data) throws ImageWriteException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(data, "png", stream);
        } catch (IOException e) {
            log.error("An exception raised from resizer. e = {}", e.getMessage());
            throw new ImageWriteException("While writing image file occurred error.");
        }
        return stream.toByteArray();

    }
}
