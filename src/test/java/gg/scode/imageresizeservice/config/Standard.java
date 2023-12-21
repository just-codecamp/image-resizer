package gg.scode.imageresizeservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Standard {

    NULL(null),
    MAX_WIDTH(500),
    MAX_HEIGHT(500),
    MIN_WIDTH(150),
    MIN_HEIGHT(150),
    OVER_WIDTH(150000),
    OVER_HEIGHT(150000);

    private final Integer value;

}
