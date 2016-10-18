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
    private static ArrayList<HotSpot> hotSpots = new ArrayList<>();	//HotSpots stored array list
    private static DecimalFormat formatter = new DecimalFormat("0.00");    	//2 decimal place format for doubles

    public static void main(String[] args) {
        readIn();			//Read input file
        initialGreeting();	//Greeting and initial messages
        weightedGraph();	//Print the weighted graph

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

    public static void readIn() {
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
    public static void initialGreeting() {
        System.out.println("Hello and welcome to Kruskal’s clustering!\n");
        System.out.println("There are " + hotSpots.size() + " hotspots.\n");
        System.out.println("The weighted graph of hotspots: \n");
    }
    public static void menu() {
        System.out.println("\nHow many emergency stations would you like?");
        System.out.println("(Enter a number between 1 and 5 to place the emergency stations.\n Enter -1 to automatically select the number of emergency stations.\n Enter 0 to exit.)\n\n");
    }

    public static double distanceBetween(HotSpot s1, HotSpot s2) {
        double distance = sqrt(pow((s2.getX()-s1.getX()),2) + pow((s2.getY()-s1.getY()),2));	//Euclidean Distance formula
        return Double.parseDouble(formatter.format(distance));									//Round to two d.p. and covert back to double
        //Note, not rounded, just chopping off everything after 2 d.p. - look into rounding!
    }

    public static void weightedGraph() {
        for(int i = 0; i < hotSpots.size(); i++) {
            String currentLine = "";
            for(int j = 0; j < hotSpots.size(); j++) {
                currentLine = currentLine.concat(distanceBetween(hotSpots.get(i), hotSpots.get(j)) + " ");
            }
            System.out.println(currentLine);
        }
    }

    public static void findCentroid(ArrayList<HotSpot> cluster) {
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

    public static void autoSelect() {
        //TODO
    }

    public static void userDefined(int numberOfStations) {
        //TODO
    }

}
