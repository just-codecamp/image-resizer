package gg.scode.imageresizeservice.services.v1;

import gg.scode.imageresizeservice.config.exceptions.AbnormalUriFormatException;
import gg.scode.imageresizeservice.config.exceptions.ImageWriteException;
import gg.scode.imageresizeservice.config.exceptions.NoImageFromURI;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.services.interfaces.Resizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResizerJava implements Resizer {

    @Override
    public byte[] resize(ImageStandard imageStandard) throws Exception {

        Optional<Image> imageOptional = extractImage(imageStandard);

        Image originImage = imageOptional.orElseThrow(
                () -> new NoImageFromURI("Can not find any image at provided URI")
        );

        BufferedImage resizedImageFrame = new BufferedImage(imageStandard.getWidth(), imageStandard.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImageFrame.createGraphics();
        graphics2D.drawImage(originImage, 0,0, imageStandard.getWidth(), imageStandard.getHeight(), null);
        graphics2D.dispose();

        return convertToByteArray(resizedImageFrame);
    }

    private Optional<Image> extractImage(ImageStandard imageStandard) throws AbnormalUriFormatException {
        try {
            URL url = URI.create(imageStandard.getUrl()).toURL();
            return Optional.ofNullable(ImageIO.read(url));
        } catch (IOException e) {
            log.error("An exception raised from resizer. e = {}", e.getMessage());
            throw new AbnormalUriFormatException("Abnormal URI is detected.");
        }
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
