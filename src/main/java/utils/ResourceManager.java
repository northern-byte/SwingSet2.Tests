package utils;

import java.util.ResourceBundle;

public class ResourceManager {

    private static final String BUNDLE_NAME = "swingset";

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getString(String key){
        return BUNDLE.getString(key);
    }
}
