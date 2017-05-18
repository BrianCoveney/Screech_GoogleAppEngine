package controller;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by brian on 25/02/17.
 */
public class ScreechServletTest {


    private ScreechServlet screechServlet;
    private double skidDistance1 = 60.0;
    private double skidDistance2 = 30.0;
    private double dragFactor = 0.75;
    private double breakingEfficiency = 1.0;
    private double expectedResult = 36.7;
    private double unexpectedResult = 30.0;
    private int fourSkidMarks = 4;
    private int threeSkidMarks = 3;
    private int twoSkidMarks = 2;
    private int oneSkidMark = 1;
    private String surfaceTypes[] = {"Cement", "Asphalt", "Gravel", "Ice", "Snow"};



    @Before
    public void setUp() throws Exception {
        screechServlet = new ScreechServlet();
    }

    @Test
    public void calculateSpeed() throws Exception {
        assertThat(expectedResult,
                is(screechServlet.calculateSpeed(skidDistance1, dragFactor, breakingEfficiency)));

        assertThat(unexpectedResult,
                is(not(screechServlet.calculateSpeed(skidDistance1, dragFactor, breakingEfficiency))));
    }


}