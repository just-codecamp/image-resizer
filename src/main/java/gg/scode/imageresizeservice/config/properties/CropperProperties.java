package gg.scode.imageresizeservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "cropper")
public class CropperProperties {

    private Auth auth;
    private Cache cache;


    @Setter
    @Getter
    public static class Cache {
        private Long ttl;
    }

    @Setter
    @Getter
    public static class Auth {

        private String headerName;
        private String algorithm;
        private String id;
        private List<String> keys;

    }

}
