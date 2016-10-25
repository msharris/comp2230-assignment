/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Hotspot
 * Description: Represents a natural disaster hotspot defining it by its ID and coordinates.
 */

public class Hotspot implements Comparable<Hotspot> {

    private int id;
    private double x;
    private double y;

    public Hotspot(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
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

    @Override
    public int compareTo(Hotspot hotspot) {
        return id - hotspot.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hotspot) {
            Hotspot hotspot = (Hotspot) obj;
            return id == hotspot.getId() || x == hotspot.getX() && y == hotspot.getY();
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

}
