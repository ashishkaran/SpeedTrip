package ask.cni.widgets;

import ask.cni.helpers.SeleniumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ask.cni.utils.DateUtils;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public abstract class DatePicker {

    private static final Logger log = LogManager.getLogger(DatePicker.class.getName());

    By dateWidget = By.xpath("//div[@id='ui-datepicker-div']");

    public class DatePickerHeader {
        private String month;
        private String year;

        public String getMonth() {
            return month;
        }

        public String getYear() {
            return year;
        }

        @Override
        public String toString() {
            return "month: " + getMonth() + "\nyear: " + getYear();
        }

        public DatePickerHeader(String month, String year) {
            this.month = month;
            this.year = year;
        }
    }

    public enum MonthType {
        FIRST("first", "Prev"),
        LAST("last", "Next");

        private String label;
        private String arrowShim;

        MonthType(String month, String arrowText) {
            this.label = month;
            this.arrowShim = arrowText;
        }

        public String getClassTag() {
            return label;
        }

        public String getArrowTitle() {
            return arrowShim;
        }
    }

    public enum MonthProp {
        MONTH("1"),
        YEAR("2");

        private String idx;

        MonthProp(String index) {
            this.idx = index;
        }

        public String getIdx() {
            return idx;
        }
    }

    private String getMonthShim(MonthType monthType) {
        return "//div[contains(@class,'monthBlock " + monthType.getClassTag() + "')]";
    }

    private By getMonthProp(MonthType monthType, MonthProp monthProp) {
        return By.xpath(getMonthShim(monthType) + "//div[@class='title']/span[" + monthProp.getIdx() + "]");
    }

    public DatePickerHeader getMonthHeader(WebDriver driver, MonthType monthType) {
        WebElement datePicker = SeleniumHelper.findElement(driver, dateWidget);
        String month = datePicker.findElement(getMonthProp(monthType, MonthProp.MONTH)).getText();
        String year = datePicker.findElement(getMonthProp(monthType, MonthProp.YEAR)).getText();
        return new DatePickerHeader(month, year);
    }

    public String getActiveDate(WebDriver driver) {
        WebElement datePicker = SeleniumHelper.findElement(driver, dateWidget);
        return datePicker.findElement(By.xpath("//a[contains(@class,'ui-state-active ')]")).getText();
    }

    public void clickDay(WebDriver driver, String dayNumber) {
        log.info("clicking on day number: " + dayNumber);
        WebElement datePicker = SeleniumHelper.findElement(driver, dateWidget);
        datePicker.findElement(By.xpath(getMonthShim(MonthType.FIRST) + "//a[contains(text(),'" + dayNumber + "')]")).click();
    }

    private void navToMonth(WebDriver driver, MonthType monthType) {
        SeleniumHelper.click(driver, By.xpath(getMonthShim(monthType) +
                "//a[@title='" + monthType.getArrowTitle() + "']"));
    }

    public void switchToMonth(WebDriver driver, MonthType monthType) {
        navToMonth(driver, monthType);
    }

    public void selectDate(WebDriver driver, DateUtils.DateObject dateObj) {
        log.info("selecting date: " + dateObj);
        String day = dateObj.getDay();
        String month = dateObj.getMonth();
        String year = dateObj.getYear();
        boolean switchYear = true;
        boolean switchMonth = true;

        // Switch till year found
        do {
            DatePickerHeader head = getMonthHeader(driver, MonthType.FIRST);
            if (head.year.equalsIgnoreCase(year)) {
                switchYear = false;
            } else {
                switchToMonth(driver, MonthType.LAST);
            }
        } while (switchYear);

        // Switch till month found
        do {
            DatePickerHeader head = getMonthHeader(driver, MonthType.FIRST);
            if (head.month.equalsIgnoreCase(month)) {
                switchMonth = false;
            } else {
                switchToMonth(driver, MonthType.LAST);
            }
        } while (switchMonth);

        // Pick date
        clickDay(driver, day);
    }

    public String selectPastDate(WebDriver driver,  DateUtils.DateObject dateObj) {
        log.info("selecting date: " + dateObj);
        String day = dateObj.getDay();
        String month = dateObj.getMonth();
        String year = dateObj.getYear();
        String temp;
        boolean switchYear = true;
        boolean switchMonth = true;

        if (SeleniumHelper.findElement(driver, dateWidget).findElement(By.xpath(getMonthShim(MonthType.FIRST) +
                "//a[@title='" + MonthType.FIRST.getArrowTitle() + "']"))
                .getAttribute("class").toLowerCase().contains("disabled")) {
            switchMonth = false;
            switchYear = false;
            temp = "disabled";
        } else {
            // Switch till year found
            do {
                DatePickerHeader head = getMonthHeader(driver, MonthType.LAST);
                if (head.year.equalsIgnoreCase(year)) {
                    switchYear = false;
                } else {
                    switchToMonth(driver, MonthType.FIRST);
                }
            } while (switchYear);
            // Switch till month found
            do {
                DatePickerHeader head = getMonthHeader(driver, MonthType.LAST);
                if (head.month.equalsIgnoreCase(month)) {
                    switchMonth = false;
                } else {
                    switchToMonth(driver, MonthType.FIRST);
                }
            } while (switchMonth);
            temp = SeleniumHelper.findElement(driver,
                    By.xpath("//div[contains(@class,'monthBlock first')]//span[text()='" + day + "']/.."))
                    .getAttribute("class");
        }
        // Pick date
        return temp;
    }
}
