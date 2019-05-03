package ashish.karan.tests;

import ashish.karan.helpers.BaseTestHelper;
import ashish.karan.helpers.SeleniumHelper;
import ashish.karan.models.BookingDetails;
import ashish.karan.models.Flight;
import ashish.karan.utils.ConfigFileReader;
import ashish.karan.utils.DateUtils;
import ashish.karan.utils.LoggerUtils;
import ashish.karan.widgets.AirlinesFilter;
import ashish.karan.enums.Airline;
import ashish.karan.enums.SortParam;
import ashish.karan.enums.SortType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ashish.karan.pages.BookingPage;
import ashish.karan.pages.Itinerary;
import ashish.karan.pages.LandingPage;

import java.lang.reflect.Method;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class BasicTest extends BaseTestHelper {

    private static final Logger log = LogManager.getLogger(BasicTest.class.getName());

    LandingPage landingPage = new LandingPage();
    BookingPage bookingPage = new BookingPage();
    Itinerary itr = new Itinerary();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        LoggerUtils.initializeLogFolder(this.getClass().getName());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        LoggerUtils.setLogFileName(
                this.getClass().getSimpleName(),
                method.getName() + "--" + DateUtils.getCurrentTimeMilliSec());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {quitWebDriver();}

    @DataProvider(name = "TestParams")

    public static Object[][] params() {

        return new Object[][] {
                {"DEL","BLR",10,false},
                {"DEL","BLR",10,true},
                {"DEL","BLR",130,false},
                {"DEL","BLR",130,true}
        };
    }

    /*
     * Basic flow
     * 1. From: DEL.
     * 2. To: BLR.
     * 3. Date: today + 10.
     * 4. Filter by only SpiceJet.
     * 5. Order by duration descending.
     * 6. Select flight with longest duration.
     * 7. Proceed to Itinerary Page.
     * 8. Verify all the input variables are correct in Itinerary page.
     */
    @Test(dataProvider = "TestParams")
    public void BasicSanity(String source, String dest, int daysAfter, boolean withStops) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("Started test ==== " + methodName + " ====");

        WebDriver driver = openBrowser();
        driver.manage().window().maximize();
        driver.get(ConfigFileReader.getValue("url"));
        log.info("URL IS: " + ConfigFileReader.getValue("url"));

        landingPage.enterOriginCity(driver, "DEL");
        landingPage.enterDestinationCity(driver, "BLR");
        landingPage.clickDateWidget(driver);
        landingPage.selectDate(driver, DateUtils.getDateAfterDateObject(daysAfter));
        landingPage.clickSearchButton(driver);
        SeleniumHelper.waitForProgressTracker(driver);
        AirlinesFilter.selectOnlyThisAirline(driver, Airline.SPICEJET);
        bookingPage.sortBy(driver, SortParam.DURATION, SortType.DESC);
        Flight flight = bookingPage.bookLongestDurationFlight(driver, withStops);

        if (flight == null) {
            Assert.assertFalse(true, "Did not get any flights");
        }

        BookingDetails bd =itr.buildBookingDetails(driver, flight);

        log.debug(bd.getPrice());
        log.debug(bd.getAirlineName());
        log.debug(bd.getSource());
        log.debug(bd.getDestination());
        log.debug(bd.getDuration());
        log.debug(bd.getDepartureTime());
        log.debug(bd.getArrivalTime());

        Assert.assertEquals(bd.getAirlineName(), Airline.SPICEJET.getAirlineTitle());
        Assert.assertEquals(bd.getSource(), source);
        Assert.assertEquals(bd.getDuration(), flight.getDuration());
        Assert.assertEquals(bd.getDepartureTime(), flight.getDepartureTime());
        Assert.assertEquals(bd.getArrivalTime(), flight.getArrivalTime());
        Assert.assertEquals(bd.getDestination(), dest);
    }

    /*
     * Mandatory fields checks in landing page
     * 1. Try to search for bookings with any input values.
     * 2. Verify that error messages are present for all mandatory fields.
     */
    @Test
    public void NoDefaults() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("Started test ==== " + methodName + " ====");

        WebDriver driver = openBrowser();
        driver.manage().window().maximize();
        driver.get(ConfigFileReader.getValue("url"));
        log.info("URL IS: " + ConfigFileReader.getValue("url"));

        String errorMessage = "You missed this";

        landingPage.clickDateWidget(driver);
        landingPage.clickSearchButton(driver);

        log.debug("from: " + landingPage.getErrorMessage(driver, landingPage.fromById));
        log.debug("to: " + landingPage.getErrorMessage(driver, landingPage.toById));
        log.debug("date: " + landingPage.getErrorMessage(driver, landingPage.datePickerById));

        Assert.assertEquals(landingPage.getErrorMessage(driver, landingPage.fromById), errorMessage);
        Assert.assertEquals(landingPage.getErrorMessage(driver, landingPage.toById), errorMessage);
        Assert.assertEquals(landingPage.getErrorMessage(driver, landingPage.datePickerById), errorMessage);
    }

    /*
     * Trying to pick past date
     * 1. Try to pick yesterday's date.
     * 2. Assert that the field has the 'disabled' class
     */
    @Test
    public void SelectPastDate() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        log.info("Started test ==== " + methodName + " ====");

        WebDriver driver = openBrowser();
        driver.manage().window().maximize();
        driver.get(ConfigFileReader.getValue("url"));
        log.info("URL IS: " + ConfigFileReader.getValue("url"));

        landingPage.clickDateWidget(driver);
        String yesterday = landingPage.selectPastDate(driver, DateUtils.getDateAfterDateObject(-1));
        Assert.assertTrue(yesterday.toLowerCase().contains("disabled"), "Yesterday date is not disabled");
    }
}
