package com.example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void test() 
    {
        Sensitivity IR1 = new DefaultSensitivity("RatesFX", "Risk_IRCurve", "GBP", "1", "6m", "OIS", new BigDecimal("200000000"));
        Assert.assertEquals(new BigDecimal("13400000000"), Simm.calculateStandard(Arrays.asList(IR1)).setScale(0, RoundingMode.HALF_UP));
    }
}
