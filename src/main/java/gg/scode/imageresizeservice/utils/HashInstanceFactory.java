package gg.scode.imageresizeservice.utils;

import gg.scode.imageresizeservice.config.properties.CropperProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@RequiredArgsConstructor
public class HashInstanceFactory {

    private final CropperProperties cropperProperties;

    @Bean
    public MessageDigest messageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(cropperProperties.getAuth().getAlgorithm());
    }

}
