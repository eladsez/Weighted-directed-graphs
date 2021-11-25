package classes;
import com.google.gson.*;

public class GeoLocation implements api.GeoLocation {

    private double x;
    private double y;
    private double z;

    public GeoLocation(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(api.GeoLocation g) {
        double temp = Math.pow(g.x() - this.x, 2) + Math.pow(g.y() - this.y, 2);
        return Math.sqrt(temp);
    }
}
