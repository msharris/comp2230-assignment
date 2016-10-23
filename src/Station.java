/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Station
 * Description: TODO
 */

import java.util.ArrayList;

public class Station {

    private int id;
    private ArrayList<Hotspot> hotspots;
    private double x;
    private double y;

    public Station(int id, ArrayList<Hotspot> hotspots) {
        this.id = id;
        this.hotspots = hotspots;
        setCentroid();
    }

    private void setCentroid() {
        // Create total count variables
        double xTotal = 0;
        double yTotal = 0;

        // Generate a total count
        for (Hotspot hotspot : hotspots) {
            xTotal += hotspot.getX();
            yTotal += hotspot.getY();
        }

        // Calculate the centroid and update location members
        x = xTotal / hotspots.size();
        y = yTotal / hotspots.size();
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ArrayList<Hotspot> getHotspots() {
        return hotspots;
    }

    @Override
    public String toString() {
        String str = "Station " + id + ":\n";
        str += "Coordinates: (" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")\n";
        str += "Hotspots: {";
        for (Hotspot hotspot : hotspots) {
            str += hotspot + ", ";
        }
        str = str.substring(0, str.length() - 2);
        str += "}\n";
        return str;
    }

}
