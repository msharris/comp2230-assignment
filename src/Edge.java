/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Edge
 * Description: Represents and edge between two natural disaster hotspots and stores the distance between them.
 */

public class Edge implements Comparable<Edge> {

    private Hotspot v1;
    private Hotspot v2;
    private double weight;

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

    @Override
    public int compareTo(Edge edge) {
        if (weight < edge.getWeight()) {
            return -1;
        } else if (weight > edge.getWeight()) {
            return 1;
        } else {
            return 0;
        }
    }

}
