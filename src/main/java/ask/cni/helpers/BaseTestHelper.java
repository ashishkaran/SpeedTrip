package ask.cni.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class BaseTestHelper {
    static Logger log = LogManager.getLogger(BaseTestHelper.class.getName());

    // ThreadLocal will keep local copy of driver
    public static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

    public WebDriver getWebDriver() {
        return threadLocal.get();
    }

    public void quitWebDriver() {
        WebDriver driver = getWebDriver();
        if (driver != null) {
            log.info("========== closing browser =======");
            driver.quit();
            threadLocal.set(null);
        }
    }

    public WebDriver openBrowser() {
        log.info("========== launching browser =======");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriverManager.chromedriver().setup();
        WebDriver driver =  new ChromeDriver(options);
        threadLocal.set(driver);
        return driver;
    }
}
