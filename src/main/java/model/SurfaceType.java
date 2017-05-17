/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package model;


public class SurfaceType implements ISurfaceType {


    private double dragFactor;


    public SurfaceType() {
    }


    public double getDragFactor() {
        return dragFactor;
    }


    // set drag factor value based on users checkbox selection
    public double setDragFactor(String choice) {
        if (choice.equalsIgnoreCase(Surface.CEMENT.toString())){
            dragFactor = Surface.CEMENT.setDragFactor(dragFactor);
        }
        else if (choice.equalsIgnoreCase(Surface.ASPHALT.toString())) {
            dragFactor = Surface.ASPHALT.setDragFactor(dragFactor);
        }
        else if (choice.equalsIgnoreCase(Surface.GRAVEL.toString())) {
            dragFactor = Surface.GRAVEL.setDragFactor(dragFactor);
        }
        else if (choice.equalsIgnoreCase(Surface.SNOW.toString())) {
            dragFactor = Surface.SNOW.setDragFactor(dragFactor);
        }
        else if (choice.equalsIgnoreCase(Surface.ICE.toString())) {
            dragFactor = Surface.ICE.setDragFactor(dragFactor);
        }
        return dragFactor;
    }

    @Override
    public double setDragFactor(double dragFactor) {
        return 0;
    }
}
