package gg.scode.imageresizeservice.api.v1;

import gg.scode.imageresizeservice.config.exceptions.NoSuchKeyException;
import gg.scode.imageresizeservice.config.properties.CropperProperties;
import gg.scode.imageresizeservice.dto.CacheVo;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.services.v1.Cropper;
import gg.scode.imageresizeservice.utils.PrimitiveKeyUtil;
import gg.scode.imageresizeservice.utils.cache.CacheUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ImageServiceController {

    private final Cropper cropper;

    private final CacheUtil cacheUtil;
    private final PrimitiveKeyUtil keyUtil;

    private final CropperProperties properties;

    @GetMapping("/resize")
    public ResponseEntity<byte[]> cropImage(
            @Valid ImageStandard imageStandard
    ) throws Exception {

        if ( !keyUtil.verify(imageStandard.getKey()) ) {
            throw new NoSuchKeyException();
        }

        byte[] cropped = cropper.resize(imageStandard);
        CacheVo caching = cacheUtil.caching(cropped);

        return ResponseEntity
                .status(201)
                .cacheControl(
                        CacheControl.maxAge(
                                        60
                                        , TimeUnit.SECONDS
                                )
                                .immutable()
                                .cachePrivate()
                )
                .eTag(caching.getData())
                .lastModified(ZonedDateTime.of(caching.getLastModified(), ZoneId.systemDefault()))
                .headers(h -> {
                    h.setContentType(MediaType.IMAGE_PNG);
                    h.setContentLength(cropped.length);
                })
                .body(cropped);
    }

}
