/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package model;

import java.util.ArrayList;

public class Skid {

    private int numberOfSkids;
    private ArrayList<Double> skidList;
    private int averageSkidDistance;


    public Skid() { }

    public Skid(int numberOfSkids, ArrayList<Double> skidList) {
        this.numberOfSkids = numberOfSkids;
        this.skidList = skidList;
        setAverageSkidDistance();
    }

    public void addSkidLengths(double skidLength) {
        this.skidList.add(skidLength);
    }

    public ArrayList<Double> getSkidList() {
        return skidList;
    }

    public void setSkidList(ArrayList<Double> skidList) {
        this.skidList = skidList;
    }

    public int getNumberOfSkids() {
        return numberOfSkids;
    }

    public void setNumberOfSkids(int numberOfSkids) {
        this.numberOfSkids = numberOfSkids;
    }


    public int getAverageSkidDistance() {
        return this.averageSkidDistance;
    }

    public void setAverageSkidDistance() {

        int sum = 0;
        for(double length : skidList) {
            sum += length;
        }

        this.averageSkidDistance = sum / this.numberOfSkids;
    }


}
