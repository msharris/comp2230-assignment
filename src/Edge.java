/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Edge
 * Description: TODO
 */

public class Edge {
    HotSpot v1;
    HotSpot v2;
    double weight;

    public Edge(HotSpot v1, HotSpot v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public HotSpot getV1() {
        return v1;
    }

    public void setV1(HotSpot v1) {
        this.v1 = v1;
    }

    public HotSpot getV2() {
        return v2;
    }

    public void setV2(HotSpot v2) {
        this.v2 = v2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
