package kr.codesquad.kioskmax.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MenuSize {

    LARGE(0),
    SMALL(1);

    private final int sizeCode;

    private MenuSize(int sizeCode) {
        this.sizeCode = sizeCode;
    }

    @JsonCreator
    public static MenuSize findBySizeCode(int sizeCode) {
        return Arrays.stream(values())
                .filter(sizes -> sizes.sizeCode == sizeCode)
                .findAny()
                .orElseThrow();
    }

    @JsonValue
    public int getSizeCode() {
        return sizeCode;
    }
}
