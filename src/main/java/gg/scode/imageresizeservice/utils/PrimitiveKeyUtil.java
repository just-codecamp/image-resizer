package gg.scode.imageresizeservice.utils;

import gg.scode.imageresizeservice.config.properties.CropperProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrimitiveKeyUtil {

    private final MessageDigest messageDigest;
    private final CropperProperties cropperProperties;

    public String getHeaderName() {
        return cropperProperties.getAuth().getHeaderName();
    }

    public boolean verify(String key) {
        String input = Base64.getEncoder().encodeToString(messageDigest.digest((cropperProperties.getAuth().getId() + key).getBytes()));

        return cropperProperties.getAuth()
                .getKeys()
                .stream()
                .anyMatch(k -> k.equals(input));
    }


}
