package utils;

import exceptions.NoSuchPropertyException;

public class Platform {

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String WINDOWS_PREFIX = "windows";
    private static final String MAC_PREFIX = "mac";
    private static final String LINUX_PREFIX = "unix";

    private static boolean isMac() {
        return OS.contains("mac");
    }

    private static boolean isWindows() {
        return OS.contains("win");
    }

    private static boolean isLinux() {
        return OS.contains("nux");
    }

    public static synchronized Prop getConfigProp(String key){
        Prop specProp = null;
            try {
                specProp = ResourceManager.getConfigProp((String.format("%s.%s", getPlatformPrefix(), key)));
            } catch (Exception ignored) {

            }

        if(specProp == null){
            throw new NoSuchPropertyException(String.format("Config property %s was not found for platform: %s ", key, OS));
        }

        return specProp;
    }

    private static String getPlatformPrefix() {
        if (isWindows()) {
            return WINDOWS_PREFIX;
        } else if (isMac()) {
            return MAC_PREFIX;
        } else if (isLinux()) {
            return LINUX_PREFIX;
        } else {
            return "";
        }
    }
}
