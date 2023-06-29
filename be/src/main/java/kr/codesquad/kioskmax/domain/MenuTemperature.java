package kr.codesquad.kioskmax.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MenuTemperature {

    HOT(0),
    ICE(1);

    private final int temperatureCode;

    private MenuTemperature(int temperatureCode) {
        this.temperatureCode = temperatureCode;
    }

    @JsonCreator
    public static MenuTemperature findByTemperatureCode(int temperatureCode) {
        return Arrays.stream(values())
                .filter(temperatures -> temperatures.temperatureCode == temperatureCode)
                .findAny()
                .orElseThrow();
    }

    @JsonValue
    public int getTemperatureCode() {
        return temperatureCode;
    }
}
