package org.example.utils;

import uk.org.webcompere.lightweightconfig.ConfigLoader;

import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties PROPERTIES = null;

    static {
        load();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void load() {
        PROPERTIES = ConfigLoader
                .loadProperties(
                        Paths.get("src", "main", "resources", "app.properties")
                );
    }
}
