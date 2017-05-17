/*
    Author: Brian Coveney
    Date: 24/02/2017
    COMP8007 OO Server Side Programming
    Assignment 1
 */

package controller;

import helpers.Const;
import model.Breaking;
import model.CarBean;
import model.Skid;
import model.SurfaceType;
import persistor.MYSQLPersistor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


@WebServlet("/ScreechServlet")
public class ScreechServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // a constant value used in the equation
    private static final int EQUATION_CONST = 30;
    private String surfaceTypes[] = {"Cement", "Asphalt", "Gravel", "Ice", "Snow"};
    private String carName;
    private double skid1, skid2, skid3, skid4;
    private int numOfSkidMarks;
    private int averageSkidLength = 0;
    private double breakingEfficiency;
    private double dragFactor;
    private double speed;
    private ArrayList<Double> skidList;
    private String cookieValue = "";
    private String cookieString;

    // references to objects
    private Skid skid;
    private CarBean carBean;


    public ScreechServlet() {
        super();
    }





    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // database
        setPersistence();

        // cookies
        setCookies(request, response);


        validateNumberOfSkidMarkIsNumeric(request, response);

        validateSidMark(request, response);

        validateSkidMarksIsNumeric(request, response);

        // set vars equal to form parameters
        carName = request.getParameter("carname");
        skid1 = Double.parseDouble(request.getParameter("skidmarklength0"));
        skid2 = Double.parseDouble(request.getParameter("skidmarklength1"));
        skid3 = Double.parseDouble(request.getParameter("skidmarklength2"));
        skid4 = Double.parseDouble(request.getParameter("skidmarklength3"));
        String surfaceChoice;
        surfaceChoice = request.getParameter("surface");

        // We can now use this empty list to hold the above parameters
        skidList = new ArrayList<>(Arrays.asList(skid1, skid2, skid3, skid4));

        // construct object and write to database
        createCarBean();

        // database method
        addCarToDatabase();

        // check that each skid length is > 0, and display error(s) if not
        validateSkidMarkIsPositiveNumber(request, response);


        validateCarName(request, response);

        // calculation methods
        skidDistance();

        breakingEfficiency();

        setDragFactor(surfaceChoice);

        calculateSpeed(averageSkidLength, dragFactor, breakingEfficiency);

        // create page containing calculation results
        StringBuffer stringBuffer = createHTMLDoc();
        response.setContentType("text/html"); // content type
        PrintWriter printWriter = response.getWriter();
        printWriter.println(stringBuffer);
        printWriter.close();

    } // end doGet()





    /**
     * This method calls the controller, which sets the persistence to our MYSQLPersistor object
     * .
     */
    private void setPersistence() {
        DBController.getInstance().setPersistor(new MYSQLPersistor());
    }


    /**
     * This method creates a CarBean object and populates it with the object's mutator methods.
     * .
     */
    private void createCarBean() {
        // Create 'CarBean' object and populate it
        carBean = new CarBean();
        carBean.setCarName(carName);
        carBean.setNumSkidMarks(numOfSkidMarks);
    }


    /**
     * This method fires the connection to the database and writes the data.
     * .
     */
    private void addCarToDatabase() {
        if(carBean != null) {
            DBController.getInstance().addCar(carBean);
        }
        DBController.getInstance().saveCar();
    }


    /**
     * This method creates a Skid object and populates it.
     * It sets the return value, equal to setAverageSkidDistance() based on the ArrayList skidList contents
     * @return averageSkidLength
     */
    private double skidDistance() {
        skid = new Skid();
        skid.setNumberOfSkids(carBean.getNumSkidMarks());
        skid.setSkidList(skidList);
        skid.setAverageSkidDistance();

        // set class scoped variable equal to the returned avg skid distance
        return averageSkidLength = skid.getAverageSkidDistance();
    }


    /**
     * This method creates a Breaking object and populates it.
     * It sets the return value, based on the number of skid marks.
     * @return breakingEfficiency
     */
    private double breakingEfficiency() {
        Breaking breaking = new Breaking();
        breaking.getNumberSkidMarks(skid);
        breaking.setBreakingEfficiency(skid.getNumberOfSkids());
        return breakingEfficiency = breaking.getBreakingEfficiency();
    }


    /**
     * This method creates a SurfaceType object.
     * It sets the return value, based on the surface choice selected in the checkbox.
     * @return dragFactor
     */
    private double setDragFactor(String surfaceChoice) {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setDragFactor(surfaceChoice);
        dragFactor = surfaceType.getDragFactor();
        return dragFactor;
    }



    /**
     * This method calculates the speed of the car, using the formula:   S = √30 * D * f * n
     * It sets the return value, based on the surface choice selected from the checkbox.
     * @return speed
     */
    protected double calculateSpeed(double skidDist, double dragFact, double brakeEfficiency) {
        double product = EQUATION_CONST * skidDist * dragFact;
        double total = Math.sqrt(product);
        speed = Math.round(total * 10) / 10.0; // round to one decimal place
        return speed;
    }


    /**
     * This method create a Cookie object, passes it parameters and sets it age.
     * It also prints the cookie value to the screen (form may need to be submitted a few times for this to display).
     * @see #getSurfaceCookies(String)
     *
     */
    private void setCookies(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter output;

        cookieString = request.getParameter("surface");
        Cookie cookie = new Cookie("surface", getSurfaceCookies(cookieString));
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        Cookie[] cookies = request.getCookies();

        response.setContentType( "text/html" );
        output = response.getWriter();

        if (cookies != null) {
            for (Cookie c : cookies) {
                cookieValue = c.getValue(); // set a String equal to the current array index
            }

            // use getSurfaceCookies() to see if there is a match, and print to screen if successful
            String surfaceCookie = getSurfaceCookies(cookieValue);
            output.print(surfaceCookie);
        }
    }


    /**
     * This method set a return value if there is a match where the Cookie is constructed and the Array surfaceTypes.
     * It sets the return value, based on the surface choice selected in the checkbox.
     * @return surfaceTypes[i] or ""
     */
    private String getSurfaceCookies(String surface) {
        for (int i = 0; i < surfaceTypes.length; i++) {
            if(surface.equals(surfaceTypes[i])) {
                return surfaceTypes[i];
            }
        }
        return "";
    }



    /**
     * Validation methods:
     *
     */

    private void validateSkidMarkIsPositiveNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check that each skid length is > 0, and display error(s) if not
        for(int j = 0; j < skidList.size(); j++) {

            if(carBean.isSkidMarkLengthValid(skidList.get(j))) {
                request.getSession().removeAttribute("errorMsg2Skid" + j);
            } else {
                request.getSession().setAttribute("errorMsg2Skid" + j, "Input error!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
    }


    private void validateSkidMarksIsNumeric(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Validate the skid marks - checks that input is a number, and display error(s) if not
        skidList = new ArrayList<>(Arrays.asList(skid1, skid2, skid3, skid4));

        // Loop through the list and try to extract a Double
        // - if successful remove the error message from the current session associated with this request
        // - otherwise, set an error message along side the offending input
        int i = 0; // my counter
        for (Double skid : skidList) {
            try {
                skid = Double.parseDouble(request.getParameter("skidmarklength" + i));
                request.getSession().removeAttribute("errorMsgSkid" + i);

            } catch (NumberFormatException e) {
                // set error message
                request.getSession().setAttribute("errorMsgSkid"+ i, "Needs to be a number!!");
                // redirect to index.jsp
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            i++; // increment the loop
        }


        // As the list was only attempting to extract a Double for validation purposes,
        // we will remove all elements currently the list
        skidList.clear();
    }


    private void validateNumberOfSkidMarkIsNumeric(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            numOfSkidMarks = Integer.parseInt(request.getParameter("skidmarks"));
            request.getSession().removeAttribute("message");
        } catch (Exception e) {
            // set error message
            request.getSession().setAttribute("message", "Must be a number!!");
            // redirect to index.jsp
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }


    public void validateSidMark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!(numOfSkidMarks < 1)) {
            request.getSession().removeAttribute("message");
        } else {
            request.getSession().setAttribute("message", "There must be a least one skid!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }


    private void validateCarName(HttpServletRequest request, HttpServletResponse response)   {

        try {
            if (carBean != null) {
                /*** Validating that car name is a string ***/
                //if car name is a string - remove the errorMessage on page load, and after user corrects input value
                if (carBean.isCarNameValid()) {
                    request.getSession().removeAttribute("errorMsg");
                } else {
                    request.getSession().setAttribute("errorMsg", "Please check you car name entry");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
        }catch (Exception e) {
            e.getMessage();
        }
    }


    /**
     * This method create a StringBuffer object and appends HTML and java code, to display a web page
     * @return stringBuff
     */
    private StringBuffer createHTMLDoc() {
        StringBuffer stringBuff = new StringBuffer();
        stringBuff.append("<html><head>\n");
        stringBuff.append("<title>Screech Web Application</title>\n");
        stringBuff.append("<style> table, th, td { border: 1px solid black; border-collapse: collapse; } th, tr,td { padding: 10px; }</style>");
        stringBuff.append("</head><body>\n");
        stringBuff.append("<jsp:getProperty name='CarBean' property='skidMarks'/>");
        stringBuff.append("<h3>Base on your figures, the skid details for the " + carName + " are:</h3>\n\n");
        stringBuff.append("<table>");
        stringBuff.append("<tr><th>Avg skid distance</th><th>Surface type</th><th>Breaking Efficiency</th><th>Calculated Speed</th></tr>");
        stringBuff.append("<tr><td>" + averageSkidLength + "'</td>");
        stringBuff.append("<td>" + getSurfaceCookies(cookieString) + "</td>");
        stringBuff.append("<td>"+ Const.displayPercent(Locale.ENGLISH, breakingEfficiency) +"</td>");
        stringBuff.append("<td>" + speed + "mph</td></tr>");
        stringBuff.append("<table>");
        stringBuff.append("</body></html>");
        return stringBuff;
    }

}