package edu.calpoly.eharlig.budget_brews;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UpdateBeerTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UpdateBeerTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UpdateBeerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testUpdateBeer()
    {
        assertTrue( true );
    }

}
