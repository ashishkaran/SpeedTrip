package ask.cni.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class LoggerUtils {

    public static String logFilePath;

    /**
     * deletes <b> className </b> folder under <b> logs </b> folder if exists
     *
     * @param className PasteList (for ex:)
     */
    public static synchronized void initializeLogFolder(String className) {
        // delete the folder if exists
        try {
            FileUtils.deleteDirectory(new File("logs/" + className));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * sets the file path to store the logs, i.e., "logs/<b>PasteList/test1.log</b>"
     *
     * @param className test1 (for ex:)
     * @param testCaseName PasteList (for ex:)
     */
    public static synchronized void setLogFileName(String className, String testCaseName) {
        logFilePath = "logs/" + className + "/" + testCaseName + ".log";

        ConfigurationSource source;
        try {
            source = new ConfigurationSource(new FileInputStream("log4j2.xml"));
            LoggerContext context = new LoggerContext(testCaseName);
            XmlConfiguration xmlConfig = new XmlConfiguration(context, source);
            Logger logger = (Logger) LogManager.getRootLogger();
            logger.getContext().start(xmlConfig);
            ThreadContext.put("logFilename", className);
            ThreadContext.put("testCaseID", testCaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
