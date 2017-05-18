package model;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by brian on 17/05/17.
 */
public class SkidTest {

    private Skid skid;
    private int numOfSkids = 4;

    @Before
    public void setUp() throws Exception {
        ArrayList<Double> skidList = new ArrayList<>(Arrays.asList(60.0, 50.0, 40.0, 30.0));
        skid = new Skid(numOfSkids, skidList);
    }

    @Test
    public void setAverageSkidDistance() throws Exception {

        MatcherAssert.assertThat(45, CoreMatchers.is(skid.getAverageSkidDistance()));

    }

}