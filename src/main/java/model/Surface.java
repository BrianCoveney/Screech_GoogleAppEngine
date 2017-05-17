/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package model;


public enum Surface implements ISurfaceType {

    CEMENT {
        @Override
        public double setDragFactor(double dragFactor) {
            return 0.75;
        }
    },
    ASPHALT {
        @Override
        public double setDragFactor(double dragFactor) {
            return 0.9;
        }

    },
    GRAVEL {
        @Override
        public double setDragFactor(double dragFactor) {
            return 0.8;
        }
    },
    SNOW {
        @Override
        public double setDragFactor(double dragFactor) {
            return 0.25;
        }
    },
    ICE {
        @Override
        public double setDragFactor( double dragFactor) {
            return 0.55;
        }
    };




}
