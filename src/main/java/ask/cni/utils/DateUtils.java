package ask.cni.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class DateUtils {

    private static final Logger log = LogManager.getLogger(DateUtils.class.getName());
    public static class DateObject {

        private String day;
        private String month;
        private String year;

        public String getDay() {return day;}

        public String getMonth() {return month;}

        public String getYear() {return year;}

        @Override
        public String toString() {return "DAY: " + getDay() + " : MONTH: " + getMonth() + " : YEAR: " + getYear();}

        /**
         * @param day Example:
         *           1. If single digit date: 8
         *           2. If multiple digits: 28
         * @param month Full month name. Ex: November
         * @param year
         */
        public DateObject(String day, String month, String year) {this.day = day;this.month = month;this.year = year;}
    }

    /**
     * @return current system time in ms
     */
    public static long getCurrentTimeMilliSec() {
        long date = System.currentTimeMillis();
        return date;
    }

    /**
     * @param timeZoneOffset
     * @return current system time in ms with timezone offset
     */
    public static long getCurrentTimeMilliSec(String timeZoneOffset) {
        long date = System.currentTimeMillis();
        TimeZone tz = TimeZone.getTimeZone(timeZoneOffset);
        int offset = tz.getOffset(date);
        return date + offset;
    }

    /**
     * @return current time stamp in a given format
     *
     * @param format
     */
    public static String getCurrentTime(String format) {
        Locale locale1 = new Locale("en");
        return new SimpleDateFormat(format, locale1).format(new Date());
    }

    /**
     * @param millisecs
     * @param format java.text.SimpleDateFormat
     * @return converts the given millisecs to date in given format
     */
    public static String getDate(long millisecs, String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTimeInMillis(millisecs);
        return formatter.format(c.getTime());
    }

    /**
     * @param dateStr say for ex: 20161230142247 (format yyyyMMddHHmmss)
     * @param timeZone
     * @return
     */
    public static Date getDate(String dateStr, TimeZone timeZone) {
        Locale locale = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", locale);
        formatter.setTimeZone(timeZone);

        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return yesterday's date
     */
    public static String getYesterdayDate() {
        Locale locale1 = new Locale("en");
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        return c.getTime().toString();
    }

    /**
     * @param format java.text.SimpleDateFormat
     * @return yesterday's date in the provided format
     */
    public static String getYesterdayDate(String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        return formatter.format(c.getTime());
    }

    /**
     * @return yesterday's date
     */
    public static Date getTomorrowDate() {
        Locale locale1 = new Locale("en");
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * @param format java.text.SimpleDateFormat
     * @return yesterday's date in given format
     */
    public static String getTomorrowDate(String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        System.out.println("Tomorrow date: " + c.getTime());
        System.out.println("c :  " + c );
        System.out.println("Hour : " + c.HOUR_OF_DAY);
        System.out.println("Minute : "+ c.MINUTE);
        return formatter.format(c.getTime());
    }

    /**
     * @param format java.text.SimpleDateFormat
     * @return today date in given format
     */
    public static String getTodayDate(String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, 0);
        return formatter.format(c.getTime());
    }

    /**
     * @param format java.text.SimpleDateFormat
     * @return date one month from now
     */
    public static String getNextMontDate(String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.MONTH, 1);
        return formatter.format(c.getTime());
    }

    /**
     * @param format java.text.SimpleDateFormat
     * @return first day on the next month
     */
    public static String getNextMontFirstDate(String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat("d,MMM,yyyy", locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, 1);
        Date firstDayOfMonth = c.getTime();
        return formatter.format(firstDayOfMonth);
    }

    /**
     * @param minutes
     * @param format java.text.SimpleDateFormat
     * @return time after x minutes
     */
    public static String getTimeAfter(int minutes, String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.MINUTE, minutes);
        return formatter.format(c.getTime());
    }

    /**
     * @param daysAfter offset days
     * @return date after x days
     */
    public static Date getDateAfter(int daysAfter) {
        Locale locale1 = new Locale("en");
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, daysAfter);
        return c.getTime();
    }

    /**
     * @param daysAfter offset days
     * @param format java.text.SimpleDateFormat
     * @return date after x days
     */
    public static String getDateAfter(int daysAfter, String format) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat(format, locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, daysAfter);
        return formatter.format(c.getTime());
    }

    /**
     * @param daysAfter offset days
     * @return DateObject with date after x days
     */
    public static DateObject getDateAfterDateObject(int daysAfter) {
        Locale locale1 = new Locale("en");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMMM-d", locale1);
        Calendar c = Calendar.getInstance(locale1);
        c.setTime(new Date());
        c.add(Calendar.DATE, daysAfter);
        String temp = formatter.format(c.getTime());
        return new DateObject(temp.substring(temp.lastIndexOf("-") + 1, temp.length()),
                temp.substring(5,temp.lastIndexOf("-")),
                temp.substring(0,4));
    }

    /**
     * @param dateStr
     * @param actualFormat dateStr's current format
     * @param expectedFormat expected format of the return str
     * @return date in expected format
     */
    public static String getFormattedDate(String dateStr, String actualFormat, String expectedFormat) {
        String returnValue = null;
        try {
            Locale locale1 = new Locale("en");
            Date date = new SimpleDateFormat(actualFormat, locale1).parse(dateStr);
            returnValue = new SimpleDateFormat(expectedFormat, locale1).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
