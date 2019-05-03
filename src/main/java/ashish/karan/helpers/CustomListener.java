package ashish.karan.helpers;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.xml.XmlSuite;
import ashish.karan.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class CustomListener extends TestListenerAdapter implements IReporter, ISuiteListener {

    protected Logger log = LogManager.getLogger(CustomListener.class.getName());
    String TIMESTAMP;
    String seperator = System.getProperty("file.separator");

    @Override
    public void onTestStart(ITestResult tr) {}

    @Override
    public void onTestFailure(ITestResult tr) {
        String className = tr.getMethod().getRealClass().getSimpleName();
        String methodName = tr.getName();
        log.error(methodName + " failed", tr.getThrowable());

        WebDriver webDriver = ((BaseTestHelper) tr.getInstance()).getWebDriver();//LocalDriverManager.getDriver();
        if (webDriver != null) {
            File scrFile = SeleniumHelper.captureScreenshot(webDriver);
            File destFile = new File("screenshot/" + TIMESTAMP + "/" + className + "/" + methodName + ".jpg");
            try {
                FileUtils.copyFile(scrFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                webDriver.close();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        String className = tr.getMethod().getRealClass().getSimpleName();
        String methodName = tr.getName();
        log.error(methodName + " skipped", tr.getThrowable());

        WebDriver webDriver = ((BaseTestHelper) tr.getInstance()).getWebDriver();
        if (webDriver != null) {

            File scrFile = SeleniumHelper.captureScreenshot(webDriver);
            File destFile = new File("screenshot" +
                    seperator + TIMESTAMP +
                    seperator + className +
                    seperator + methodName + ".jpg");
            try {
                FileUtils.copyFile(scrFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {}

    public void onStart(ISuite suite) {
        TIMESTAMP = DateUtils.getCurrentTime("yyyyMMddHHmms");
    }

    public void onFinish(ISuite suite) {}
}
