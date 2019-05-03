package ashish.karan.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class StringUtils {

    private static final Logger log = LogManager.getLogger(StringUtils.class.getName());

    /**
     * Converts Time in the format XXh XXm to seconds
     * @param duration
     * @return
     */
    public static Long convertFlightDurationToSeconds(String duration) {
        String hours = "";
        String min = "";
        String smallDur = duration.toLowerCase();
        if (smallDur.contains("h") && smallDur.contains("m")) {
            hours = duration.substring(0,duration.indexOf("h"));
            min = duration.substring(duration.lastIndexOf("h") + 2, duration.length() -1);
        } else if (smallDur.contains("h")) {
            hours = duration.substring(0,duration.indexOf("h"));
        } else if (smallDur.contains("m")) {
            min = smallDur.substring(0,duration.lastIndexOf("m"));
        }
        Long longDuration = 0L;
        if (hours != "") {
            longDuration += Long.valueOf(hours)*60*60;
        }
        if (min != "") {
            longDuration += Long.valueOf(min)*60;
        }
        return longDuration;
    }
}
