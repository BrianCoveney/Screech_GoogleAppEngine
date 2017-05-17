/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package model;

import helpers.Const;

import java.util.Locale;


public class Breaking {


    private double breakingEfficiency;
    private int numberSkidMarks;


    public Breaking() { }


    public double getBreakingEfficiency() {
        return breakingEfficiency;
    }


    public int getNumberSkidMarks(Skid skid) {
        return numberSkidMarks = skid.getNumberOfSkids();
    }


    public double setBreakingEfficiency(int numberSkidMarks) {
        if (numberSkidMarks == 4) {
            breakingEfficiency = Const.ONE_HUNDRED_PERCENT;
        } else if (numberSkidMarks == 3) {
            breakingEfficiency = Const.SEVENTY_PERCENT;
        } else if (numberSkidMarks == 2) {
            breakingEfficiency = Const.SIXTY_PERCENT;
        } else if (numberSkidMarks == 1) {
            breakingEfficiency = Const.THIRTY_PERCENT;
        }
        return this.breakingEfficiency;
    }

    @Override
    public String toString() {

        return Const.displayPercent(Locale.ENGLISH, breakingEfficiency);

    }
}
