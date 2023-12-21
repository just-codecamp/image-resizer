package gg.scode.imageresizeservice.config.handlers;

import gg.scode.imageresizeservice.config.exceptions.AbnormalUriFormatException;
import gg.scode.imageresizeservice.config.exceptions.ImageWriteException;
import gg.scode.imageresizeservice.config.exceptions.NoImageFromURI;
import gg.scode.imageresizeservice.config.exceptions.NoSuchKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * This exception can raise when can not load page of URI or exist image at page
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Void> fileProcessException(IOException e) {
        return ResponseEntity.badRequest()
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AbnormalUriFormatException.class)
    public ResponseEntity<Void> abnormalUriFormatException(AbnormalUriFormatException e) {
        return ResponseEntity.badRequest()
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoImageFromURI.class)
    public ResponseEntity<Void> noImageFromURI(NoImageFromURI e) {
        return ResponseEntity.badRequest()
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoSuchKeyException.class)
    public ResponseEntity<String> noSuchKeyException(NoSuchKeyException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Could not find your key. please check your key again");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageWriteException.class)
    public ResponseEntity<Void> imageWriteException(ImageWriteException e) {
        return ResponseEntity.badRequest()
                .build();
    }

}
