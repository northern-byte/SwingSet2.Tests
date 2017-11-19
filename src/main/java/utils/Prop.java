package utils;

import java.awt.*;
import java.util.Arrays;

public class Prop{
    private String colorSeparator = ":";
    private String value;

    Prop(String value) {
        this.value = value;
    }

    public String String(){
        return value;
    }

    public int Int(){
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public double Double(){
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public Color Color(){
        Integer[] rgb = Arrays.stream(value.split(colorSeparator)).map(Integer::parseInt).toArray(Integer[]::new);
        return new Color(rgb[0], rgb[1], rgb[2]);
    }
}
