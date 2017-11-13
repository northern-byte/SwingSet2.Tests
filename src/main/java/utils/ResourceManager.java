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
    private static boolean _propsAreLoaded = false;

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private static synchronized InputStream getConfig() {
        InputStream config = ResourceManager.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
        if (config == null) {
            throw new MissingResourceException("Can't find config file", CONFIG_NAME, "");
        }
        return config;
    }

    public static synchronized String getResString(String key) {
        try {
            return BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            throw e;
        }
    }

    public static synchronized Prop getProp(String key) {
        loadProps();
        String value = _properties.getProperty(key);
        if (value == null) {
            throw new NoSuchPropertyException(String.format("There is no property: %s", key));
        }
        return new Prop(value);
    }

    public static synchronized Prop getSpecProp(String spec, String key){
        return getProp(String.format("%s.%s", spec, key));
    }

    public static synchronized void loadProps() {
        if (!_propsAreLoaded) {
            try {
                _properties = new Properties();
                _properties.load(getConfig());
                _propsAreLoaded = true;
            } catch (IOException e) {
                throw new FailedToLoadPropertiesException("Failed to load properties");
            }
        }
    }
}
