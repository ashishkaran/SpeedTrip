package ask.cni.enums;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public enum Airline {
    SPICEJET("SpiceJet"),
    AIR_ASIA("Air Asia"),
    AIR_INDIA("Air India"),
    GO_AIR("GoAir"),
    INDIGO("IndiGo"),
    JET_AIRWAYS("Jet Airways"),
    VISTARA("Vistara");

    private String airline;
    Airline(String airline) {this.airline = airline;}
    public String getAirlineTitle() {
        return airline;
    }
}
