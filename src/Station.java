/*
 * Students: Mitchell Harris (3184553), Joshua Wilson (3166052)
 * Course: COMP2230 Algorithmics
 * Assessment: Assignment
 *
 * Class name: Station
 * Description: TODO
 */


import java.util.*;

public class Station {
    private int idCounter = 1;
    private String id;
    private double xVal;
    private double yVal;
    private ArrayList<HotSpot> stationHotSpots = new ArrayList<>();

    public Station(int xVal, int yVal) {
        id = "Station " + idCounter;
        this.xVal = xVal;
        this.yVal = yVal;
        idCounter++;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public double getX() {
        return xVal;
    }

    public double getY() {
        return yVal;
    }

    public ArrayList<HotSpot> getHotSpots() {
        return stationHotSpots;
    }

    @Override
    public String toString() {	//Untested
        String spotList = "";														//List of hotspots to be kept in a string format "x,y,z"
        for(int i =0; i < stationHotSpots.size();i++) {								//For loop is to get the id of each of the hotspots at this station, then concatenates them to a string
            if(i != stationHotSpots.size()-1) {										//If statement just controls whether or not a comma is placed after the number (as the last number doesnt have a comma after it)
                spotList = spotList.concat(stationHotSpots.get(i).getId() + ",");	//If not last number, add a ","
            } else {
                spotList = spotList.concat(stationHotSpots.get(i).getId()+"");			//Otherwise don't add anything
            }

        }
        return id + ": \nCoordinates: (" + xVal + ", " + yVal + ")\nHotSpots: {" + spotList + "}";
    }

}