package ask.cni.pages;

import ask.cni.models.Flight;
import ask.cni.utils.FlightComparator;
import ask.cni.helpers.SeleniumHelper;
import ask.cni.enums.SortParam;
import ask.cni.enums.SortType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ask.cni.utils.StringUtils;
import org.testng.Assert;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class BookingPage {

    private static final Logger log = LogManager.getLogger(BookingPage.class.getName());

    private By navSorter = By.id("sorterTpl");
    private By singleResultTableByCss = By.cssSelector("table.resultUnit.flightDetailsLink");
    private By bookByCss = By.cssSelector("button.booking");
    private By durationClassName = By.className("duration");
    private By depByCss = By.cssSelector("th.depart");
    private By arrByCss = By.cssSelector("th.arrive");
    private By costByCss = By.cssSelector("th#BaggageBundlingTemplate.price");
    private By routeByCss = By.cssSelector("td.route");
    private By stopsByCss = By.cssSelector("td.duration");

    public void sortBy(WebDriver driver, SortParam sortParam, SortType sortType) {
        WebElement sorter = SeleniumHelper.findElement(driver, navSorter);
        By temp = By.xpath("//a[@data-sort='" + sortParam.getSortKey() + "']");
        boolean clickAgain = true;
        SeleniumHelper.click(driver, sorter.findElement(temp));
        String className = sorter.findElement(temp).getAttribute("class");
        do {
            if (className.contains(sortType.getSortClass())) {
                clickAgain = false;
            } else {
                SeleniumHelper.click(driver,sorter.findElement(temp));
                className = sorter.findElement(temp).getAttribute("class");
            }
        } while (clickAgain);
    }

    public List<WebElement> findFlights(WebDriver driver) {
        return SeleniumHelper.findElements(driver, singleResultTableByCss);
    }

    public Flight bookLongestDurationFlight(WebDriver driver, boolean withStops) {
        Flight flight = getLongestDurationFlight(driver, withStops);
        if (flight == null) {
            Assert.assertFalse(true, "Couldnt find any flights for this testcase");
        }
        SeleniumHelper.click(driver, flight.getElement().findElement(bookByCss));
        return flight;
    }

    private Flight getLongestDurationFlight(WebDriver driver, final boolean withStops) {
        List<WebElement> flights = findFlights(driver);
        List<Flight> flightDurations = new ArrayList<Flight>();

        for (WebElement flight : flights) {
            List<String> flightRoutes;
            int stops = 0;
            WebElement routes = flight.findElement(routeByCss);
            flightRoutes = Arrays.asList(routes.getText().split(" → "));
                String stopString = flight.findElement(stopsByCss).getText();
                if (stopString.toLowerCase().contains("non-")) {
                    stops = 0;
                } else {
                    stops = Integer.valueOf(stopString.substring(0, stopString.lastIndexOf(" stop")));
                }
            flightDurations.add(new Flight(
                    flight,
                    StringUtils.convertFlightDurationToSeconds(
                    flight.findElement(durationClassName).getText()),
                    flight.findElement(depByCss).getText(),
                    flight.findElement(arrByCss).getText(),
                    flightRoutes,
                    stops,
                    flight.findElement(costByCss).getText()
                    ));
        }
        Collections.sort(flightDurations, new FlightComparator().reversed());
        Predicate<Flight> byStops = flight -> flight.hasStops() == withStops;
        Flight returnFlight = null;
        try {
            returnFlight = flightDurations.stream().filter(byStops).collect(Collectors.<Flight> toList()).get(0);
        } catch (Exception e) {
            log.error("Couldnt find any flights : " + withStops + "\n" + e.getMessage());
        }
        return returnFlight;
    }
}
