package gg.scode.imageresizeservice.config.exceptions;

public class NoSuchKeyException extends Exception {

    public NoSuchKeyException() {
        super();
    }

    public NoSuchKeyException(String message) {
        super(message);
    }

    public NoSuchKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchKeyException(Throwable cause) {
        super(cause);
    }
}
