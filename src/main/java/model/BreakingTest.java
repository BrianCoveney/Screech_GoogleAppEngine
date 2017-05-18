package model;

import helpers.Const;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by brian on 17/05/17.
 */
public class BreakingTest {

    private Breaking breaking;
    private Skid skid1, skid2, skid3, skid4;

    @Before
    public void setUp() throws Exception {

        skid1 = new Skid(); skid1.setNumberOfSkids(4);
        skid2 = new Skid(); skid2.setNumberOfSkids(3);
        skid3 = new Skid(); skid3.setNumberOfSkids(2);
        skid4 = new Skid(); skid4.setNumberOfSkids(1);

        breaking = new Breaking();
        breaking.getNumberSkidMarks(skid1);

    }

    @Test
    public void setBreakingEfficiency() throws Exception {

        MatcherAssert.assertThat(Const.ONE_HUNDRED_PERCENT, CoreMatchers.is(breaking.setBreakingEfficiency(skid1.getNumberOfSkids())));
        MatcherAssert.assertThat(Const.SEVENTY_PERCENT, CoreMatchers.is(breaking.setBreakingEfficiency(skid2.getNumberOfSkids())));
        MatcherAssert.assertThat(Const.SIXTY_PERCENT, CoreMatchers.is(breaking.setBreakingEfficiency(skid3.getNumberOfSkids())));
        MatcherAssert.assertThat(Const.THIRTY_PERCENT, CoreMatchers.is(breaking.setBreakingEfficiency(skid4.getNumberOfSkids())));

    }

}