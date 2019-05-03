package ashish.karan.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class SeleniumHelper {

    private static final Logger log = LogManager.getLogger(SeleniumHelper.class.getName());
    private static final int MAX_WAIT_TIMEOUT = 30;
    private static final By tipsyXPath = By.xpath("//div[@class='tipsyInner']");

    /**
     * searches for webElement
     *
     * @param driver
     * @param by
     */
    public static WebElement findElement(WebDriver driver, By by) {
        waitForPageContainsWebElement(driver, by);

        log.debug("finding element " + by.toString());
        return driver.findElement(by);
    }

    /**
     * searches for webElements
     *
     * @param driver
     * @param by
     * @return list of webElements
     */
    public static List<WebElement> findElements(WebDriver driver, By by) {
        waitForPageContainsWebElements(driver, by);

		log.debug("finding elements " + by.toString());
        return driver.findElements(by);
    }

    /**
     * clicks on the element, like button, link, radio button, checkbox
     *
     * @param driver
     * @param locator
     */
    public static void click(WebDriver driver, By locator) {
        waitForByLocators(driver, locator);
        log.info("on element: " + locator.toString());
        driver.findElement(locator).click();
    }

    /**
     * clicks on the element, like button, link, radio button, checkbox
     *
     * @param driver
     * @param element
     */
    public static void click(WebDriver driver, WebElement element) {
        waitForElementLocators(driver, element);
        log.info("on element: " + element.getText());
        element.click();
    }

    /**
     * enters the text into the textbox
     *
     * @param driver
     * @param by
     * @param data to be entered into the textbox
     */
    public static void enterInput(WebDriver driver, By by, String data) {
        waitForElementToBeVisible(driver, by);
        WebElement textBox = findElement(driver, by);
        if (textBox.getAttribute("type") != null) {
            if (!textBox.getAttribute("type").equals("file")) {
                textBox.clear();
            }
        }
        log.info("' " + data + " ' in " + by.toString());
        textBox.sendKeys(data);
    }

    /**
     * Waits for the webElement identified by the locator to be visible
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementToBeVisible(WebDriver driver, By locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to be visible");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for the webElement to be visible
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementToBeVisible(WebDriver driver, WebElement locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to be visible");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    /**
     * Waits for the webElement identified by the locator to be clickable
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementToBeClickable(WebDriver driver, By locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to be clickable");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for the webElement to be visible
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementToBeClickable(WebDriver driver, WebElement locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to be clickable");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for the webElement identified by the locator to be present in dom
     *
     * @param driver
     * @param locator
     */
    public static void waitForPageContainsWebElement(WebDriver driver, By locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to be present on the page");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits for all the webElement identified by the locator to be present in dom
     *
     * @param driver
     * @param locator
     */
    public static void waitForPageContainsWebElements(WebDriver driver, By locator) {
        log.debug("Waiting for the elements: " + locator.toString() + " to be present on the page");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Waits for the webElement identified by the locator to vanish
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementVanish(WebDriver driver, By locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to vanish");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits for the webElement to vanish
     *
     * @param driver
     * @param locator
     */
    public static void waitForElementVanish(WebDriver driver, WebElement locator) {
        log.debug("Waiting for the element: " + locator.toString() + " to vanish");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOf(locator));
    }

    /**
     * @param driver
     * @param by
     * @param waitTime
     * @return true if element is not visible or else false in a given time
     */
    public static boolean waitForWebElementNotVisible(WebDriver driver, By by, long... waitTime) {
        boolean result = false;
        long end;
        if (waitTime == null || waitTime.length == 0) {
            end = System.currentTimeMillis() + MAX_WAIT_TIMEOUT;
        } else {
            end = System.currentTimeMillis() + waitTime[0];
        }
        while (System.currentTimeMillis() < end) {
            try {
                if (driver.findElement(by).isDisplayed() == false) {
                    result = true;
                    break;
                }
            } catch (Exception e) {
                break; // fine
            }
        }
        return result;
    }

    /**
     * Waits for the progress tracker in booking page to not be visisble
     *
     * @param driver
     */
    public static void waitForProgressTracker(WebDriver driver) {
        log.info("Waiting for the progress tracker");
        WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='progressTracker']")));
    }

    /**
     * Sleeps for the number of ms provided
     *
     * @param sleepTimeinMs
     */
    public static void sleep(int sleepTimeinMs) {
        log.debug("Sleeping for: " + sleepTimeinMs / 1000 + " seconds");
        try {
            Thread.sleep(sleepTimeinMs);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Hovers on an element that triggers the target to appear and then clicks on target
     *
     * @param driver
     * @param hoverTrigger
     * @param hoverTarget
     */
    public static void hoverClick(WebDriver driver, WebElement hoverTrigger, WebElement hoverTarget) {
        log.info("hovering element : " + hoverTrigger.getTagName() + " to click: " + hoverTarget.getTagName());

        Actions action = new Actions(driver);
        action.moveToElement(hoverTrigger).click(hoverTarget).build().perform();

    }

    /**
     * Highlights the  webElement. Used for debugging
     *
     * @param driver
     * @param element
     */
    public static void highlightElement(WebDriver driver, WebElement element) {
        log.debug("Highlighting element" + element.getText());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 1px solid red;');",
                element);
    }

    /**
     * Takes screenshot of the web page
     *
     * @param driver
     * @return
     */
    public static File captureScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    /**
     * Combining all the waits for By elements in one method.
     * Waiting for custom loaders can go here.
     * @param driver
     * @param locator
     */
    private static void waitForByLocators(WebDriver driver, By locator) {
        waitForPageContainsWebElement(driver, locator);
        waitForElementToBeVisible(driver, locator);
        waitForElementToBeClickable(driver, locator);
        waitForElementVanish(driver, tipsyXPath);
        waitForWebElementNotVisible(driver, tipsyXPath);
    }

    /**
     * Combining all the waits for WebElements in one method.
     * Waiting for custom loaders can go here.
     * @param driver
     * @param locator
     */
    private static void waitForElementLocators(WebDriver driver, WebElement locator) {
        waitForElementToBeVisible(driver, locator);
        waitForElementToBeClickable(driver, locator);
        waitForElementVanish(driver, tipsyXPath);
        waitForWebElementNotVisible(driver, tipsyXPath);
    }
}
