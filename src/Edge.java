/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Edge
 * Description: TODO
 */

public class Edge {

    Hotspot v1;
    Hotspot v2;
    double weight;

    public Edge(Hotspot v1, Hotspot v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Hotspot getV1() {
        return v1;
    }

    public Hotspot getV2() {
        return v2;
    }

    public double getWeight() {
        return weight;
    }

}
