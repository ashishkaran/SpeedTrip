package ashish.karan.pages;

import ashish.karan.helpers.SeleniumHelper;
import ashish.karan.widgets.DatePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class LandingPage extends DatePicker {

    private static final Logger log = LogManager.getLogger(LandingPage.class.getName());

    public By fromById = By.id("FromTag");
    public By toById = By.id("ToTag");
    public By datePickerById = By.id("DepartDate");
    public By searchById = By.id("SearchBtn");
    private By errorMsgXPath = By.xpath(".//following-sibling::small");

    public void enterOriginCity(WebDriver driver, String origin) {
        SeleniumHelper.enterInput(driver, fromById, origin);
    }

    public void enterDestinationCity(WebDriver driver, String destination) {
        SeleniumHelper.enterInput(driver, toById, destination);
    }

    public void clickDateWidget(WebDriver driver) {
        log.info("Opening the date widget");
        SeleniumHelper.click(driver, datePickerById);
    }

    public void clickSearchButton(WebDriver driver) {
        SeleniumHelper.click(driver, searchById);
    }

    public String getErrorMessage(WebDriver driver, By by) {
        return SeleniumHelper.findElement(driver, by).findElement(errorMsgXPath).getText();
    }
}
