package gg.scode.imageresizeservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Slf4j
public class UtilityTests {

    public static Stream<String> getLoremStrings() {
        return UrlUtil.generateLoremUrlStrings(30).stream();
    }

    public static Stream<String> getMalformedUrlStrings() {
        return UrlUtil.generateMalformedUrlStrings().stream();
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getLoremStrings")
    @DisplayName("log getLoremStrings()")
    void test1(String url) throws Exception {
        log.info("URL = {}", url);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getMalformedUrlStrings")
    @DisplayName("log getMalformedUrlStrings()")
    void test2(String url) throws Exception {
        log.info("URL = {}", url);
    }

}
