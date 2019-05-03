package ashish.karan.pages;

import ashish.karan.helpers.SeleniumHelper;
import ashish.karan.models.BookingDetails;
import ashish.karan.models.Flight;
import ashish.karan.utils.StringUtils;
import ashish.karan.enums.ItrDetailType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class Itinerary {

    private static final Logger log = LogManager.getLogger(Itinerary.class.getName());

    private By priceXPath = By.xpath("//div[@class='upSell']//strong[@id='totalFare']//span[@id='counter']");

    // Values fetched from Booking Detail
    private By airNameShim = By.xpath("//small/preceding-sibling::span");
    private By airportCodeShim = By.xpath(".//span[@class='placeTime']//span[@rel='tTooltip']");
    private By travelDateShim = By.xpath(".//strong");
    private By durationShim = By.xpath("//abbr");
    private By durationWithConnectionsShim = By.xpath("//div[@class='itinerary']//small");

    private WebElement getBookingDetail(WebDriver driver, String source_dest) {
        return SeleniumHelper.findElement(driver, By.xpath("//ul[@data-sector='" + source_dest + "']"));
    }

    private WebElement getBookingDetailType(WebElement element, ItrDetailType itrDetailType) {
        return element.findElement(By.xpath(itrDetailType.getDetail()));
    }

    private String getAirlineName(WebElement element) {
        return element.findElement(airNameShim).getText();
    }

    private String getAirportCode(WebElement element) {
        return element.findElement(airportCodeShim).getText();
    }

    private String getTravelDate(WebElement element) {
        return element.findElement(travelDateShim).getText();
    }

    private Long getJourneyDuration(WebElement element, boolean hasConnections) {
        if (hasConnections) {
            String duration = element.findElement(durationWithConnectionsShim).getText();
            return StringUtils.convertFlightDurationToSeconds(
                    duration.substring(duration.lastIndexOf(":") + 2, duration.length()));
        } else {return StringUtils.convertFlightDurationToSeconds(element.findElement(durationShim).getText());}
    }

    public BookingDetails buildBookingDetails(WebDriver driver, Flight flight) {
        List<String> routingCodes = flight.getRoute();
        for(String routeCode : routingCodes) {
            log.info("route code is: " + routeCode);
        }
        log.info("stops: " + flight.getStops());
        BookingDetails bookingDetails = new BookingDetails();
        List<String> bookingRoutes = new ArrayList<String>();

        for (int i = 0; i < routingCodes.size() -1 ; i++) {
            bookingRoutes.add(routingCodes.get(i).concat("_").concat(routingCodes.get(i+1)));
        }

        WebElement origin;
        WebElement destination;
        String airlineName;
        Long duration;

        if (bookingRoutes.size() > 1) {
            WebElement first = getBookingDetail(driver, bookingRoutes.get(0));
            origin = getBookingDetailType(first, ItrDetailType.ORIGIN);
            destination = getBookingDetailType(
                    getBookingDetail(driver, bookingRoutes.get(bookingRoutes.size()-1)), ItrDetailType.DESTINATION);
            airlineName = getAirlineName(first);
            duration = getJourneyDuration(first, true);

        } else {
            WebElement element = getBookingDetail(driver, bookingRoutes.get(0));
            origin = getBookingDetailType(element, ItrDetailType.ORIGIN);
            destination = getBookingDetailType(element, ItrDetailType.DESTINATION);
            airlineName = getAirlineName(element);
            duration = getJourneyDuration(element, false);
        }

//        WebElement booking = getBookingDetail(driver, source_dest);
//        WebElement origin = getBookingDetailType(booking, ItrDetailType.ORIGIN);
//        WebElement destination = getBookingDetailType(booking, ItrDetailType.DESTINATION);

        bookingDetails.setAirlineName(airlineName);
        bookingDetails.setDuration(duration);
        bookingDetails.setSource(getAirportCode(origin));
        bookingDetails.setDepartureTime(getTravelDate(origin));
        bookingDetails.setArrivalTime(getTravelDate(destination));
        bookingDetails.setDestination(getAirportCode(destination));
        bookingDetails.setPrice(driver.findElement(priceXPath).getText());
        return bookingDetails;
    }
}
