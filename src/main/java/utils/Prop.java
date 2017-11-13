package utils;

public class Prop{
    String value;

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
}
