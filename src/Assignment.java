/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Assignment
 * Description: TODO
 */

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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
        System.out.print("There are " + hotspots.size() + " hotspots.\n\n");
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
        double distance = sqrt(pow((h2.getX() - h1.getX()), 2) + pow((h2.getY() - h1.getY()), 2));

        // Round the distance to 2 decimal places
        BigDecimal bd = new BigDecimal(distance);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        // Return the rounded distance
        return bd.doubleValue();
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
                System.out.print("\nEntry not valid.\n");
                continue;
            }

            // Run user selection
            if (selection < -1 || selection > 5) {
                System.out.print("\nEntry not valid.\n");
            } else if (selection == -1) {
                autoSelect();
            } else if (selection != 0) {
                userDefined(selection);
            } else {
                System.out.print("\nThank you for using Kruskal’s Clustering. Bye.\n");
            }
        }
    }

    private void printMenu() {
        System.out.print("How many emergency stations would you like?\n");
        System.out.print("(Enter a number between 1 and 5 to place the emergency stations.\n");
        System.out.print("Enter -1 to automatically select the number of emergency stations.\n");
        System.out.print("Enter 0 to exit.)\n\n");
    }

    private void findCentroid(ArrayList<Hotspot> cluster) {
        double xTotal = 0;
        double yTotal = 0;
        for(int i = 0; i < cluster.size(); i++) {
            xTotal = xTotal + cluster.get(i).getX();
            yTotal = yTotal + cluster.get(i).getY();
        }
        double centroidX = xTotal/cluster.size();
        double centroidY = yTotal/cluster.size();
        //New station(centroidX, centroidY)?
    }

    private void autoSelect() {
        //TODO
    }

    private void userDefined(int numberOfStations) {
        //TODO
        ArrayList<ArrayList<Edge>> disjointSets = null;    ///maybe??
        ArrayList<Edge> addedEdges = new ArrayList<>();
        ArrayList<Edge> unaddedEdges = edges;
        while(disjointSets.size() < numberOfStations) {
            Edge tempEdge = searchShortestEdge(unaddedEdges);
            if(!createsCycle(tempEdge, addedEdges)) {  //Adding this edge doesn't create a cycle
                //TODO: add it to the added edges. Note we need to think about how we will have added edges, but also disjoint sets of added edges
                //Disjoint sets in an array list which holds a list of edges in that subset? see above("maybe")

            } else {    //This edge does create a cycle, and shouldn't be added to the set. (Will also be removed so it's not considered every time after this, if it creates a cycle now, it always will)
                unaddedEdges.remove(tempEdge);  //Remove this edge, it will create a cycle
            }
        }
    }

    private Edge searchShortestEdge(ArrayList<Edge> list) {
        //TODO: This will just search through the unadded edges and return the next shortest one
        return null;
    }

    private boolean createsCycle(Edge test, ArrayList<Edge> addedEdges) {
        //TODO: This will take the added edges and test to see if the edge we are looking to add will create a cycle
        return false;
    }

}
