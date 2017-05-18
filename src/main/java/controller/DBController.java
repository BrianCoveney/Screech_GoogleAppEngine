/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package controller;

import model.CarBean;
import persistor.IPersistor;

import java.util.ArrayList;


public class DBController {

    private static DBController instance;
    private IPersistor persistor;
    private PersistenceMode persistenceMode;
    private ArrayList<CarBean> carBeansList = new ArrayList<CarBean>();


    /*
     Singleton pattern requires a private constructor,
     to override the public default constructor.
     This prevents other classes instantiating it.
    */
    private DBController() { }


    /**
     * Singleton has a static instance of itself
     * @link { https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm }
     * @see ScreechServlet#setPersistence()
     * @see ScreechServlet#createCarBean()
     * @return instance
     */
    public static DBController getInstance() {
        if(instance == null) {
            instance = new DBController();
        }
        return instance;
    }



    /**
     * This method adds CarBean objects, to our ArrayList of CarBean objects.
     * @see ScreechServlet#createCarBean()  [ Method Declaration ]
     * @param carBean This is the first paramter to addNum method
     * @return Nothing.
     */
    public void addCar(CarBean carBean) { this.carBeansList.add(carBean); }


    /**
     * This method uses our MYSQLPersistor class,
     * which implements the abstract method of IPersistor - void writeCars(...)
     * This is where it is called:
     * @see ScreechServlet#createCarBean()  [ Method Declaration ]
     * @return Nothing.
     */
    public void saveCar() { this.persistor.writeCars(this.carBeansList); }


    // set persistor
    public void setPersistor(IPersistor persistor) { this.persistor = persistor; }

}
