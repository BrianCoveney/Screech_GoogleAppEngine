/*
    Author: Brian Coveney
    Date: 24/02/2017

    COMP8007 OO Server Side Programming
    Assignment 1
 */

package persistor;

import com.google.appengine.api.utils.SystemProperty;
import controller.DBController;
import model.CarBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class MYSQLPersistor implements IPersistor {
    /*
    * Intellij supports @link and @see in comments.
    * Keep the Ctrl key pressed and hover your mouse pointer over the link.
    * @see https://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#@see
    */

    /**
     * Connection interface.
     * @see { https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html }
     * A connection (session) with a specific database. SQL statements are executed
     * and results are returned within the context of a connection.
     */
    private Connection dbConnection;

    /**
     * AutoCloseable interface
     * @see { https://docs.oracle.com/javase/8/docs/api/java/lang/AutoCloseable.html }
     * This closes and releases the MySQL connection.
     */
    private ArrayList<AutoCloseable> dbObjects;

    // my database connection constants
    private static final String DB_PASS = "bossdog12";
    private static final String DB_NAME = "screech";
    private static final String DB_USER = "brian";
    private static final String DB_PORT = "3306";
    private static final String DB_LOCAL = "localhost";
    private static final String DB_REMOTE_NOT_GOOGLE = "82.118.226.76";


    // in MySQL on a VPS, create user and grant access to db:
    // CREATE USER '<username>'@'<your_ip>' IDENTIFIED BY '<password>';
    // GRANT ALL ON <database>.* TO '<username>' IDENTIFIED BY '<password>';
    public MYSQLPersistor(){

        dbObjects = new ArrayList<AutoCloseable>();

        try {


            String url;

            if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production)
            {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url="jdbc:google:mysql://orbital-stream-167920:us-central1:myinstance/screech?user=root";
                dbConnection = DriverManager.getConnection(url);
            }
            else {
                Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://"+ DB_REMOTE_NOT_GOOGLE +":"+DB_PORT+"/"+DB_NAME+"?user="+DB_USER+"&password="+DB_PASS;
                dbConnection = DriverManager.getConnection(url);
            }


            // test connection
            if(this.dbConnection != null) {
                System.out.println("Connected to MySQL!");
            } else {
                System.out.println("Connection Failed!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * This method is used to loop through a list of the CarBean object,
     * then performs a MySQL insert statement, which adds values to the DB table.
     * @see DBController#saveCar() [ Method Declaration ]
     * @param cars This is the first parameter to addNum method
     * @return Nothing.
     * @throws SQLException
     */
    @Override
    public void writeCars(ArrayList<CarBean> cars) {
        try {
            for (CarBean car : cars) {

                /**
                 * @link { https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html }
                 * PreparedStatement is an object which represents a precompiled SQL statement.
                 * @params sql - an SQL statement that may contain one or more '?' IN parameter placeholders
                 * @returns a new default PreparedStatement object containing the pre-compiled SQL statement
                 * @throws SQLException - if a database access error occurs or this method is called on a closed connection
                 */
                PreparedStatement preparedStatement =
                        dbConnection.prepareStatement(
                                "INSERT INTO cars " +
                                        "(car_name, skid_marks, the_date)" +
                                        "VALUES(?, ?, ?)");

                preparedStatement.setString(1, car.getCarName());
                preparedStatement.setInt(2, car.getNumSkidMarks());
                preparedStatement.setDate(3, new java.sql.Date(car.getDate().getTime()));

                preparedStatement.executeUpdate();
                dbObjects.add(preparedStatement);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to close the MySQL connection.
     * @see DBController#saveCar() [ Method Declaration ]
     * @return Nothing.
     * @throws Exception
     */
    public void close() {
        try{
            for(AutoCloseable curr : dbObjects) {
                curr.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
























