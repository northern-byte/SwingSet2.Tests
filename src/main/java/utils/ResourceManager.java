package utils;

import exceptions.FailedToLoadPropertiesException;
import exceptions.NoSuchPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ResourceManager {

    private static final String BUNDLE_NAME = "swingset";
    private static final String CONFIG_NAME = "config.properties";
    private static final String SPEC_NAME = "specifications.properties";
    private static Properties configProperties = null;
    private static Properties specificationProperties = null;
    private static boolean configPropertiesAreLoaded = false;
    private static boolean specificationPropertiesAreLoaded = false;

    private static ResourceBundle BUNDLE;

    public static void loadBundle() {
        BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static synchronized InputStream getResInputStream(String path) {
        InputStream inputStream = ResourceManager.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new MissingResourceException("Can't find resource file", path, "");
        }
        return inputStream;
    }

    public static synchronized String getResString(String key) {
        try {
            return BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            throw e;
        }
    }
    static synchronized Prop getConfigProp(String key){
        loadConfigProperties();
        String value = configProperties.getProperty(key);
        if (value == null) {
            throw new NoSuchPropertyException(String.format("There is no property: %s", key));
        }
        return new Prop(value);
    }

    static synchronized Prop getSpecProp(String key){
        loadSpecificationProperties();
        String value = specificationProperties.getProperty(key);
        if (value == null) {
            throw new NoSuchPropertyException(String.format("There is no specification property: %s", key));
        }
        return new Prop(value);
    }

    public static synchronized void loadConfigProperties(){
        if(!configPropertiesAreLoaded) {
            configProperties = loadProps(CONFIG_NAME);
            configPropertiesAreLoaded = true;
        }
    }

    public static synchronized void loadSpecificationProperties(){
        if(!specificationPropertiesAreLoaded) {
            specificationProperties = loadProps(SPEC_NAME);
            specificationPropertiesAreLoaded = true;
        }
    }

    private static synchronized Properties loadProps(String source) {
            try {
                Properties properties = new Properties();
                properties.load(getResInputStream(source));
                return properties;
            } catch (IOException e) {
                throw new FailedToLoadPropertiesException("Failed to load properties");
            }
    }
}
