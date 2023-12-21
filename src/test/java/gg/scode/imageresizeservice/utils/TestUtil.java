package gg.scode.imageresizeservice.utils;

import java.util.UUID;

public class TestUtil {

    public static String PNG = "png";

    public static String generateFileName(String ext) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return String.format("%s_%s_%s.%s"
                , stackTrace[2].getClassName().substring(stackTrace[2].getClassName().lastIndexOf('.') + 1)
                , stackTrace[2].getMethodName()
                , UUID.randomUUID()
                , ext
                );
    }

}
