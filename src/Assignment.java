/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Assignment
 * Description: Solves a clustering problem for emergency services. It ensures that two natural disaster hotspots
 *              belonging to different emergency stations are as far away from each other as possible, and that hotspots
 *              belonging to the same emergency station are as close together as possible so that emergency services can
 *              move from one hotspot to another.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Assignment {

    private ArrayList<Hotspot> hotspots;
    private ArrayList<Edge> edges;
    private double interIntraRatio; // Because we can't return 2 things from a method

    public static void main(String[] args) {
        Assignment assignment = new Assignment();
        assignment.run();
    }

    private Assignment() {
        hotspots = new ArrayList<>();
        edges = new ArrayList<>();
        interIntraRatio = 0.0;
    }

    private void run() {
        printGreeting();
        readInputFile();
        printHotspots();
        mainMenu();
        printFarewell();
    }

    private void printGreeting() {
        System.out.print("Hello and welcome to Kruskal’s clustering!\n\n");
    }

    private void readInputFile() {
        try {
            // Open a Scanner on the input file
            Scanner sc = new Scanner(new File("input.txt"));

            // Read the input file
            while (sc.hasNext()) {
                // Get hotspot data and create a new Hotspot
                int id = sc.nextInt();
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                Hotspot hotspot = new Hotspot(id, x, y);

                // Add the hotspot to the list if it hasn't previously been defined
                if (hotspots.contains(hotspot)) {
                    throw new Exception();
                } else {
                    hotspots.add(hotspot);
                }
            }

            // Close the Scanner
            sc.close();
        } catch (Exception e) {
            // Catch any exceptions that can occur when reading the input file
            System.out.print("There was a problem reading the input file.\n\n");
            printFarewell();
            System.exit(1);
        }
    }

    private void printHotspots() {
        // Dynamically display the number of hotspots depending on the number of hotspots
        if (hotspots.size() == 1) {
            System.out.print("There is " + hotspots.size() + " hotspot.\n\n");
        } else {
            System.out.print("There are " + hotspots.size() + " hotspots.\n\n");
        }

        // Print the weighted graph of hotspots
        System.out.print("The weighted graph of hotspots:\n\n");
        weightedGraph();

        // Print two blank lines as per the assignment specs
        System.out.print("\n\n");
    }

    private void weightedGraph() {
        // Determine the weighted graph of hotspots
        for (int i = 0; i < hotspots.size(); i++) {
            // Create an output string to store the intermediate results for the current hotspot
            String currentLine = "";

            // Determine the weight between the current hotspot and all other hotspots
            for (int j = 0; j < hotspots.size(); j++) {
                // Get the weight between the hotspots
                double weight = distanceBetween(hotspots.get(i), hotspots.get(j));

                // Create an edge and add it to the set if it's not a loop and it's not already added in reverse
                Edge edge = new Edge(hotspots.get(i), hotspots.get(j), weight);
                if (i != j && !containsReverse(edge)) {
                    edges.add(edge);
                }

                // Append the weight to the output string
                currentLine += String.format("%.2f", weight) + " ";
            }

            // We have found the weight between the current hotspot and all other hotspots - print the results
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
        // Create a Scanner to read user input and declare a variable to store that input
        Scanner sc = new Scanner(System.in);
        int selection = -2;

        // Always return to the main menu until the user chooses to exit (option 0)
        while (selection != 0) {
            // Print the menu
            printMenu();

            // Get the users selection
            try {
                selection = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("\n"); // Print a blank line as per the assignment specs
                printInvalidEntry();
                continue;
            }

            // Print a blank line as per the assignment specs
            System.out.print("\n");

            // Run the user's selection
            if (selection < -1 || selection > hotspots.size()) {
                printInvalidEntry();
            } else if (selection == -1) {
                automaticSelection();
            } else if (selection != 0) {
                manualSelection(selection);
            } else {
                break;
            }
        }
    }

    private void printMenu() {
        System.out.print("How many emergency stations would you like?\n");
        System.out.print("(Enter a number between 1 and " + hotspots.size() + " to place the emergency stations.\n");
        System.out.print("Enter -1 to automatically select the number of emergency stations.\n");
        System.out.print("Enter 0 to exit.)\n\n");
    }

    private void printInvalidEntry() {
        System.out.print("Entry not valid.\n\n");
    }

    private void automaticSelection() {
        // Ensure there are at least 3 hotspots because otherwise we cannot calculate the optimal number of stations
        if (hotspots.size() < 3) {
            System.out.print("This option cannot be selected for less than 3 hotspots.\n\n");
            return;
        }

        // Find and print the optimal number of stations (there may be more than one optimal number)
        ArrayList<Integer> optimalNumberOfStations = findOptimalNumberOfStations();
        printOptimalNumberOfStations(optimalNumberOfStations);

        // Generate the first optimal number of stations
        ArrayList<Station> stations = generateStations(optimalNumberOfStations.get(0));

        // Print the stations and the InterCD/IntraCD ratio
        printStations(stations);
        printInterIntraRatio();
    }

    private void manualSelection(int numberOfStations) {
        // Generate the stations
        ArrayList<Station> stations = generateStations(numberOfStations);

        // Print the stations and the inter-clustering distance
        printStations(stations);
        printInterClusteringDistance(stations);
    }

    private void printStations(ArrayList<Station> stations) {
        for (Station station : stations) {
            System.out.print(station + "\n\n");
        }
    }

    private void printOptimalNumberOfStations(ArrayList<Integer> optimalNumberOfStations) {
        String str = "Number of stations: ";
        for (Integer i : optimalNumberOfStations) {
            str += i + ", ";
        }
        str = str.substring(0, str.length() - 2); // Remove the trailing ", " from the output string
        System.out.print(str + "\n");
    }

    private void printInterClusteringDistance(ArrayList<Station> stations) {
        if (stations.size() > 1) {
            double interClusteringDistance = interClusteringDistance(stations);
            System.out.print("Inter-clustering distance: " + String.format("%.2f", interClusteringDistance) + "\n\n");
        } else {
            System.out.print("Inter-clustering distance: Not applicable.\n\n");
        }
    }

    private void printInterIntraRatio() {
        System.out.print("\nInterCD/IntraCD = " + String.format("%.2f", interIntraRatio) + "\n\n");
    }

    private double interClusteringDistance(ArrayList<Station> stations) {
        // Ensure that there are at least 2 stations
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

    private double intraClusteringDistance(ArrayList<Station> stations) {
        // Ensure that there is at least 1 station
        if (stations.size() < 1) {
            throw new IllegalArgumentException();
        }

        // Calculate the intra-clustering distance
        double intraCd = 0.0;
        for (Station station : stations) {
            ArrayList<Hotspot> hotspots = station.getHotspots();
            for (int i = 1; i < hotspots.size(); i++) {
                double distance = distanceBetween(hotspots.get(0), hotspots.get(i));
                if (distance > intraCd) {
                    intraCd = distance;
                }
            }
        }

        // Return the intra-clustering distance
        return intraCd;
    }

    private ArrayList<Integer> findOptimalNumberOfStations() {
        // Ensure there are at least 3 hotspots because otherwise the optimal number of stations cannot be calculated
        if (hotspots.size() < 3) {
            throw new IllegalArgumentException();
        }

        // Create variables to store the max InterCD/IntraCD ratio and the optimal number of stations
        interIntraRatio = 0.0;
        ArrayList<Integer> optimalNumberOfStations = new ArrayList<>();

        // For 2 <= k <= n-1, where k is the number of stations and n is the number of hotspots, determine the optimal
        // number of stations for the given number of hotspots
        for (int numberOfStations = 2; numberOfStations < hotspots.size(); numberOfStations++) {
            // Generate the stations
            ArrayList<Station> stations = generateStations(numberOfStations);

            // Calculate the InterCD/IntraCD ratio
            double interCd = interClusteringDistance(stations);
            double intraCd = intraClusteringDistance(stations);
            double ratio = interCd / intraCd;

            // Clear and add the current number of stations to the list i the ratio is the largest found so far,
            // else, if it is equal to the largest found so far, add it to the list
            if (ratio > interIntraRatio) {
                interIntraRatio = ratio;
                optimalNumberOfStations.clear();
                optimalNumberOfStations.add(numberOfStations);
            } else if (ratio == interIntraRatio) {
                optimalNumberOfStations.add(numberOfStations);
            }
        }

        // Return the optimal number of stations
        return optimalNumberOfStations;
    }

    private ArrayList<Station> generateStations(int numberOfStations) {
        // Get the clusters using Kruskal's algorithm
        ArrayList<ArrayList<Hotspot>> clusters = kruskal(numberOfStations);

        // Sort the clusters so that the cluster with the smallest hotspot ID is at the start of the list
        Collections.sort(clusters, (cluster1, cluster2) -> cluster1.get(0).compareTo(cluster2.get(0)));

        // Create a Station for each cluster and add it to the list
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
            ArrayList<Hotspot> disjointSet = makeset(hotspot);
            disjointSets.add(disjointSet);
        }

        // Sort the edges by weight
        Collections.sort(edges);

        // For each edge, if the edge doesn't create a cycle (i.e. its vertices aren't already in the same component),
        // then add it to the component (i.e. merge the disjoint sets)
        for (Edge edge : edges) {
            // Break if we have obtained the number of minimum spanning trees we are looking for
            if (disjointSets.size() == numberOfTrees) {
                break;
            }

            // Merge the disjoint sets if the adding the edge doesn't create a cycle
            if (findset(disjointSets, edge.getV1()) != findset(disjointSets, edge.getV2())) {
                union(disjointSets, edge.getV1(), edge.getV2());
            }
        }

        // Return the MST
        return disjointSets;
    }

    private ArrayList<Hotspot> makeset(Hotspot hotspot) {
        // Create and return a new set containing the hotspot
        ArrayList<Hotspot> set = new ArrayList<>();
        set.add(hotspot);
        return set;
    }

    private ArrayList<Hotspot> findset(ArrayList<ArrayList<Hotspot>> disjointSets, Hotspot hotspot) {
        // Find and return the disjoint set that contains the hotspot
        for (ArrayList<Hotspot> disjointSet : disjointSets) {
            if (disjointSet.contains(hotspot)) {
                return disjointSet;
            }
        }

        // The hotspot was not found in any disjoint set
        return null;
    }

    private void union(ArrayList<ArrayList<Hotspot>> disjointSets, Hotspot v1, Hotspot v2) {
        // Find the two disjoint sets containing v1 and v2, respectively
        ArrayList<Hotspot> v1Set = findset(disjointSets, v1);
        ArrayList<Hotspot> v2Set = findset(disjointSets, v2);

        // Remove the two disjoint sets from the list of disjoint sets
        disjointSets.remove(v1Set);
        disjointSets.remove(v2Set);

        // Make a new disjoint set and merge the two previously found disjoint sets
        ArrayList<Hotspot> disjointSet = new ArrayList<>();
        disjointSet.addAll(v1Set);
        disjointSet.addAll(v2Set);

        // Add the new disjoint set to the list of disjoint sets
        disjointSets.add(disjointSet);
    }

    private void printFarewell() {
        System.out.print("Thank you for using Kruskal’s Clustering. Bye.\n");
    }

}
