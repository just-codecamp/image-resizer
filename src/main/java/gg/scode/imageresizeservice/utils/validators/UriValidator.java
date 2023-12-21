package gg.scode.imageresizeservice.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class UriValidator implements ConstraintValidator<HttpsURL, String> {

    // exclude port validation
//    private final Pattern PORT_PATTERN = Pattern.compile("^\\d{1,5}$");
//    private final Pattern HOSTNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.-]+$");
//    private final Pattern HOSTNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.-]+$");

    private String[] schemes = {"http","https"};


    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
//        try {
////            URI uri = new URI(url);
////            if ( !uri.getScheme().equals("https") ) return false;
////            if ( uri.getPath() != null && uri.getPath().contains(" ")) return false;
////            if ( uri.getQuery() != null && uri.getQuery().contains(" ")) return false;
//            // exclude port validation
////            if (uri.getPort() < 0 || !PORT_PATTERN.matcher(String.valueOf(uri.getPort())).matches()) return false;
////            if (uri.getHost() == null || !HOSTNAME_PATTERN.matcher(uri.getHost()).matches() || uri.getHost().matches(".*[^a-zA-Z0-9-].*")) return false;
//
//            return true;
//        } catch (URISyntaxException e) {
//            return false;
//        }
    }

}
