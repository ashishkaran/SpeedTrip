package ask.cni.utils;

import ask.cni.models.Flight;

import java.util.Comparator;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class FlightComparator implements Comparator<Flight> {
    public int compare(Flight flightA, Flight flightB) {
        return flightA.getDuration().compareTo(flightB.getDuration());
    }
}
