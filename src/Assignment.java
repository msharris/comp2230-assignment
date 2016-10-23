/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Assignment
 * Description: TODO
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Assignment {

    private ArrayList<Hotspot> hotspots = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();  // Excludes edges that loop on one vertex

    public static void main(String[] args) {
        Assignment assignment = new Assignment();
        assignment.run();
    }

    private void run() {
        readInputFile();
        printGreeting();
        weightedGraph();
        System.out.print("\n\n"); // Two blank lines as per assignment specs
        mainMenu();
    }

    private void readInputFile() {
        try {
            // Open a Scanner on the input file
            Scanner sc = new Scanner(new File("input.txt"));

            // Read the input and create new hotspots
            while (sc.hasNext()) {
                int id = sc.nextInt();
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                Hotspot hotspot = new Hotspot(id, x, y);
                hotspots.add(hotspot);
            }

            // Close the Scanner
            sc.close();
        } catch (Exception e) {
            // Catch any exceptions that can go wrong when reading the input file
            System.out.print("An exception occurred reading the input file\n");
            System.exit(1);
        }
    }

    // TODO Display "There is 1 hotspot." when input has only 1 hotspot?
    private void printGreeting() {
        System.out.print("Hello and welcome to Kruskal’s clustering!\n\n");
        if (hotspots.size() == 1) {
            System.out.print("There is " + hotspots.size() + " hotspot.\n\n");
        } else {
            System.out.print("There are " + hotspots.size() + " hotspots.\n\n");
        }
        System.out.print("The weighted graph of hotspots:\n\n");
    }

    private void weightedGraph() {
        for (int i = 0; i < hotspots.size(); i++) {
            String currentLine = "";
            for (int j = 0; j < hotspots.size(); j++) {
                // Get weight between hotspots
                double weight = distanceBetween(hotspots.get(i), hotspots.get(j));

                // Create an edge and add it to the set if it's not a loop and it's not already added in reverse
                Edge edge = new Edge(hotspots.get(i), hotspots.get(j), weight);
                if (i != j && !containsReverse(edge)) {
                    edges.add(edge);
                }

                // Append the weight to the output string
                currentLine += String.format("%.2f", weight) + " ";
            }
            System.out.print(currentLine + "\n");
        }
    }

    private double distanceBetween(Hotspot h1, Hotspot h2) {
        // Use the Euclidean distance formula
        return Math.sqrt(Math.pow((h2.getX() - h1.getX()), 2) + Math.pow((h2.getY() - h1.getY()), 2));
    }

    private boolean containsReverse(Edge edge) {
        // Create a new edge with the vertices reversed
        Edge reverse = new Edge(edge.getV2(), edge.getV1(), edge.getWeight());

        // Check if the reversed edge is already in the set
        return edges.contains(reverse);
    }

    private void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int selection = -2;
        while (selection != 0) {
            // Print menu and get user selection
            printMenu();
            try {
                selection = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("\nEntry not valid.\n\n");
                continue;
            }

            // Run user selection
            if (selection < -1 || selection > hotspots.size()) {
                System.out.print("\nEntry not valid.\n\n");
            } else if (selection == -1) {
                generateOptimalStations();
            } else if (selection != 0) {
                ArrayList<Station> stations = generateStations(selection);
                printStations(stations);
                printInterClusteringDistance(stations);
            } else {
                System.out.print("\nThank you for using Kruskal’s Clustering. Bye.\n");
            }
        }
    }

    private void printMenu() {
        System.out.print("How many emergency stations would you like?\n");
        System.out.print("(Enter a number between 1 and " + hotspots.size() + " to place the emergency stations.\n");
        System.out.print("Enter -1 to automatically select the number of emergency stations.\n");
        System.out.print("Enter 0 to exit.)\n\n");
    }

    private void printStations(ArrayList<Station> stations) {
        System.out.print("\n"); // Blank line as per assignment specs
        for (Station station : stations) {
            System.out.print(station + "\n");
        }
    }

    private void printInterClusteringDistance(ArrayList<Station> stations) {
        if (stations.size() > 1) {
            double interClusteringDistance = interClusteringDistance(stations);
            System.out.print("Inter-clustering distance: " + String.format("%.2f", interClusteringDistance) + "\n\n");
        } else {
            System.out.print("Inter-clustering distance: Not applicable.\n\n");
        }
    }

    private double interClusteringDistance(ArrayList<Station> stations) {
        // Ensure that there are more than 2 stations
        if (stations.size() < 2) {
            throw new IllegalArgumentException();
        }

        // Calculate the inter-clustering distance
        double interCd = distanceBetween(stations.get(0).getHotspots().get(0), stations.get(1).getHotspots().get(0));
        ArrayList<Hotspot> s1Cluster = stations.get(0).getHotspots();
        for (int i = 1; i < stations.size(); i++) {
            ArrayList<Hotspot> s2Cluster = stations.get(i).getHotspots();
            for (Hotspot h : s1Cluster) {
                for (Hotspot j : s2Cluster) {
                    if (distanceBetween(h, j) < interCd) {
                        interCd = distanceBetween(h, j);
                    }
                }
            }
        }

        // Return the inter-clustering distance
        return interCd;
    }

    private void generateOptimalStations() {
        // TODO Work out the optimal number of stations and then call generateStations with the result
    }

    private ArrayList<Station> generateStations(int numberOfStations) {
        // Get the clusters using Kruskal's algorithm
        ArrayList<ArrayList<Hotspot>> clusters = kruskal(numberOfStations);

        // Sort the clusters so that the cluster with the smallest hotspot ID is at the start of the list
        Collections.sort(clusters, (cluster1, cluster2) -> cluster1.get(0).compareTo(cluster2.get(0)));

        // Create the stations
        ArrayList<Station> stations = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            ArrayList<Hotspot> cluster = clusters.get(i);
            Station station = new Station(i + 1, cluster);
            stations.add(station);
        }

        // Return the stations
        return stations;
    }

    private ArrayList<ArrayList<Hotspot>> kruskal(int numberOfTrees) {
        // Create a list to store the disjoint sets
        ArrayList<ArrayList<Hotspot>> disjointSets = new ArrayList<>();

        // Create a disjoint set for each hotspot
        for (Hotspot hotspot : hotspots) {
            ArrayList<Hotspot> disjointSet = makeSet(hotspot);
            disjointSets.add(disjointSet);
        }

        // Sort the edges by weight
        Collections.sort(edges);

        for (Edge edge : edges) {
            if (disjointSets.size() == numberOfTrees) {
                break;
            }
            if (find(disjointSets, edge.getV1()) != find(disjointSets, edge.getV2())) {
                union(disjointSets, edge.getV1(), edge.getV2());
            }
        }

        // Return the MST
        return disjointSets;
    }

    private ArrayList<Hotspot> makeSet(Hotspot hotspot) {
        ArrayList<Hotspot> set = new ArrayList<>();
        set.add(hotspot);
        return set;
    }

    private ArrayList<Hotspot> find(ArrayList<ArrayList<Hotspot>> disjointSets, Hotspot hotspot) {
        for (ArrayList<Hotspot> disjointSet : disjointSets) {
            if (disjointSet.contains(hotspot)) {
                return disjointSet;
            }
        }
        throw new IllegalArgumentException();
    }

    private void union(ArrayList<ArrayList<Hotspot>> disjointSets, Hotspot v1, Hotspot v2) {
        // Find the two sets containing v1 and v2, respectively
        ArrayList<Hotspot> v1Set = null;
        ArrayList<Hotspot> v2Set = null;
        for (ArrayList<Hotspot> disjointSet : disjointSets) {
            if (disjointSet.contains(v1)) {
                v1Set = disjointSet;
                disjointSets.remove(disjointSet);
                break;
            }
        }
        for (ArrayList<Hotspot> disjointSet : disjointSets) {
            if (disjointSet.contains(v2)) {
                v2Set = disjointSet;
                disjointSets.remove(disjointSet);
                break;
            }
        }

        // Make a new disjoint set and add it to the list of disjoint sets
        ArrayList<Hotspot> disjointSet = new ArrayList<>();
        disjointSet.addAll(v1Set);
        disjointSet.addAll(v2Set);
        disjointSets.add(disjointSet);
    }

}
