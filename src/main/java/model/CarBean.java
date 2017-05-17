/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package model;

import java.io.Serializable;
import java.util.Date;

public class CarBean implements ICarBean, Serializable{

    // class properties
    private String carName;
    private int numSkidMarks = 0;
    private Date date;



    // class variable
    private String stringRegex = "^[A-Za-z, ]++$";


    private ISurfaceType iSurfaceType;

    // A JavaBean is a public class with default (no argument) constructor.
    // @link { http://wiki.apidesign.org/wiki/JavaBean }
    public CarBean() {
        setDate();
    }



    @Override
    public void setSurface(ISurfaceType iSurfaceType) {
        this.iSurfaceType = iSurfaceType;
    }

    @Override
    public ISurfaceType getSurface() {
        return this.iSurfaceType;
    }


    // accessor method - allow the client to retrieve the state of the object
    public String getCarName() {
        return carName;
    }

    // mutator method - allow the client change the state of the object
    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setNumSkidMarks(int numSkidMarks) {
        this.numSkidMarks = numSkidMarks;
    }

    public int getNumSkidMarks() {
        return numSkidMarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = new Date();
    }


    // Validation methods

    public boolean isCarNameValid() {
        if (this.carName.matches(stringRegex)) {
            return true;
        }
        return false;
    }


    public boolean isSkidMarkLengthValid(Double skidLength) {
        if (skidLength >= 0) {
            return true;
        }
        return false;
    }
}

























