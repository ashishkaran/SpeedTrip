package ashish.karan.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class ConfigFileReader {

    private static final Logger log = LogManager.getLogger(ConfigFileReader.class.getName());
    public static String getValue(String key) {
        return getPropertiesFile().getProperty(key);
    }

    private static Properties getPropertiesFile() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("./config/config.properties"));
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
