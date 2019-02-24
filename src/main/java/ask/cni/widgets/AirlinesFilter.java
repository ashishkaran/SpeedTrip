package ask.cni.widgets;

import ask.cni.helpers.SeleniumHelper;
import ask.cni.enums.Airline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public abstract class AirlinesFilter {

    private static final Logger log = LogManager.getLogger(AirlinesFilter.class.getName());

    private static By airlinesWidget = By.xpath("//nav[@class='airlines']" +
            "//span[contains(@original-title,'multi-airline')]/../../..");

    public enum AirlineProp {
        INPUT("/preceding-sibling::input"),
        ONLY("/following-sibling::a");

        private String prop;
        AirlineProp(String prop) {this.prop = prop;}

        public String getProp() {return prop;}
    }

    private static WebElement buildAirlinesProp(WebDriver driver, String airlineName, AirlineProp airlineProp) {
        WebElement widget = SeleniumHelper.findElement(driver, airlinesWidget);
        return widget.findElement(By.xpath("//span[@original-title='" + airlineName + "']" + airlineProp.getProp()));
    }
    public static void selectOnlyThisAirline(WebDriver driver, Airline airline) {
        log.info("Selecting just : " + airline);
        SeleniumHelper.hoverClick(driver,
                buildAirlinesProp(driver, airline.getAirlineTitle(), AirlineProp.INPUT),
                buildAirlinesProp(driver, airline.getAirlineTitle(), AirlineProp.ONLY));
    }
}
