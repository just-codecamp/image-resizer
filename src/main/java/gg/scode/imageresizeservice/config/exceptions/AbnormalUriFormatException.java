package gg.scode.imageresizeservice.config.exceptions;

public class AbnormalUriFormatException extends Exception {
    public AbnormalUriFormatException(String message) {
        super(message);
    }

    public AbnormalUriFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbnormalUriFormatException(Throwable cause) {
        super(cause);
    }
}
