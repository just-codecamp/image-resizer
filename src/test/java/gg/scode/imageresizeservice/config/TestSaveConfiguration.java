package gg.scode.imageresizeservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestSaveConfiguration {

    public static final String TEST_DIR = "tests/outputs";

    private boolean permanentMode;
    private boolean initialMode;

}
