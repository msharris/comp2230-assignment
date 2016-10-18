/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: HotSpot
 * Description: TODO
 */

public class HotSpot {

    private int id;
    private double xVal;
    private double yVal;

    public HotSpot(int id, double xVal, double yVal) {
        this.id = id;
        this.xVal = xVal;
        this.yVal = yVal;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public double getX() {
        return xVal;
    }

    public double getY() {
        return yVal;
    }


    @Override
    public String toString() {
        return id + ": (" + xVal + "," + yVal + ")";
    }
}
