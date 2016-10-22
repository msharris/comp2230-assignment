/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Assignment
 * Description: TODO
 */

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Assignment {

    private ArrayList<HotSpot> hotSpots = new ArrayList<>();        //HotSpots stored array list
    private ArrayList<Edge> setOfEdges = new ArrayList<>();         //The set of all edges in the graph (excluding edges that loop on one vertex)
    private DecimalFormat formatter = new DecimalFormat("0.00");    //2 decimal place format for doubles

    public static void main(String[] args) {
        Assignment assignment = new Assignment();
        assignment.run();
    }

    private void run() {
        readIn();			//Read input file
        initialGreeting();	//Greeting and initial messages
        weightedGraph();	//Print the weighted graph, this also collects the set of all edges

        int selection;
        Scanner sc = new Scanner(System.in);
        menu();
        selection = sc.nextInt();
        while(selection != 0) {
            if(selection < -1 || selection > 5) {
                System.out.println("Entry not valid.");
            }
            if(selection == -1) {
                autoSelect(); //~> Interclustering Dist in this method?
            } else {
                userDefined(selection); //~> Interclustering Dist in this method?
            }

            menu();
        }
        System.out.println("Thank you for using Kruskal’s Clustering. Bye.");
    }

    private void readIn() {
        File file = new File("input.txt");
        try {
            Scanner in = new Scanner(file);
            while(in.hasNext()) {
                int tempId = in.nextInt();
                double tempX = in.nextDouble();
                double tempY = in.nextDouble();
                HotSpot tempHotSpot = new HotSpot(tempId, tempX, tempY);
                hotSpots.add(tempHotSpot);
            }
            in.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    private void initialGreeting() {
        System.out.println("Hello and welcome to Kruskal’s clustering!\n");
        System.out.println("There are " + hotSpots.size() + " hotspots.\n");
        System.out.println("The weighted graph of hotspots: \n");
    }
    private void menu() {
        System.out.println("\nHow many emergency stations would you like?");
        System.out.println("(Enter a number between 1 and 5 to place the emergency stations.\n Enter -1 to automatically select the number of emergency stations.\n Enter 0 to exit.)\n\n");
    }

    private double distanceBetween(HotSpot s1, HotSpot s2) {
        double distance = sqrt(pow((s2.getX()-s1.getX()),2) + pow((s2.getY()-s1.getY()),2));	//Euclidean Distance formula
        return Double.parseDouble(formatter.format(distance));									//Round to two d.p. and covert back to double
        //TODO: Note, not rounded, just chopping off everything after 2 d.p. - look into rounding!
    }

    private void weightedGraph() {
        for(int i = 0; i < hotSpots.size(); i++) {
            String currentLine = "";
            for(int j = 0; j < hotSpots.size(); j++) {
                currentLine = currentLine.concat(distanceBetween(hotSpots.get(i), hotSpots.get(j)) + " ");
                Edge tempEdge = new Edge(hotSpots.get(i), hotSpots.get(j), distanceBetween(hotSpots.get(i), hotSpots.get(j)));
                if(i != j && !containsReverse(tempEdge)) {    //This condition removes edges that are loops on single vertices && edges that are between the same vertices, just different order
                    setOfEdges.add(tempEdge);
                }
            }
            System.out.println(currentLine);
        }
    }

    private boolean containsReverse(Edge original) {
        Edge swapped = new Edge(original.getV2(), original.getV1(), original.getWeight());  //Create a new edge, with the vertices swapped
        if(setOfEdges.contains(swapped)) {          //If the swapped vertices create an edge thats already in the set of edges
            return true;                            //True - don't add to list again
        } else {
            return false;                           //False - add it to the list
        }
    }



    private void findCentroid(ArrayList<HotSpot> cluster) {
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
        ArrayList<Edge> unaddedEdges = setOfEdges;
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
