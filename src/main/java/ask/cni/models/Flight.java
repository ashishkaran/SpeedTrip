package ask.cni.models;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public class Flight {
    private WebElement element;
    private Long duration;
    private String departureTime;
    private String arrivalTime;
    private String cost;
    private List<String> route;
    private int stops;

    public WebElement getElement() {
        return element;
    }

    public Long getDuration() {
        return duration;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public String getPrice() {return cost;}
    public List<String> getRoute() {return route;}
    public int getStops() {return stops;}
    public boolean hasStops() {if (stops != 0) return true; else return false;}

    public Flight(WebElement element, Long duration, String departureTime, String arrivalTime,
                  List<String> route, int stops, String cost) {

        this.element = element;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route = route;
        this.stops = stops;
        this.cost = cost;
    }
}
