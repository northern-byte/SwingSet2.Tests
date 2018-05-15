package utils;

import implementations.wrappers.TestImage;

import java.awt.*;
import java.util.Arrays;

public class Prop {
    private String colorSeparator = ":";
    private String value;

    Prop(String value) {
        this.value = value;
    }

    public String asString() {
        return value;
    }

    public int asInt() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public double asDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public Color asColor() {
        Integer[] rgb = Arrays.stream(value.split(colorSeparator)).map(Integer::parseInt).toArray(Integer[]::new);
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public TestImage asTestImage() {
        return new TestImage(value);
    }

    public boolean asBoolean(){
        return Boolean.parseBoolean(value);
    }
}
