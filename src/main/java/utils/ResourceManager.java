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
    private static Properties _properties = null;

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private static InputStream getConfig() {
        InputStream config = ResourceManager.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
        if (config == null) {
            throw new MissingResourceException("Can't find config file", CONFIG_NAME, "");
        }
        return config;
    }

    public static String getString(String key) {
        try {
            return BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            throw e;
        }
    }

    public static synchronized String getProp(String key) {
        if (_properties == null) {
            try {
                _properties = new Properties();
                _properties.load(getConfig());
            } catch (IOException e) {
                throw new FailedToLoadPropertiesException("Failed to load properties");
            }
        }
        String value = _properties.getProperty(key);
        if (value == null) {
            throw new NoSuchPropertyException(String.format("There is no property: %s", key));
        }
        return value;
    }
}
