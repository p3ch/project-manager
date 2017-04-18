package layers.domain.events;

/**
 * Domain event
 */
public class CarSold {

    private String vin;

    public CarSold(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }
}