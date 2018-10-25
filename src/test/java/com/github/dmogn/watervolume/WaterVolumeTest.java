package com.github.dmogn.watervolume;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests of water volume calculation algorithm.
 *
 * @author Dmitry Ognyannikov
 */
public class WaterVolumeTest {

    private int testLandscape[];

    public WaterVolumeTest() {
    }

    @Before
    public void setUp() {
        testLandscape = new int[]{5, 2, 3, 4, 5, 4, 0, 3, 1};
    }

    @Test
    public void calculateWaterAmountTest() {
        final long volume = WaterVolume.calculateWaterAmount(testLandscape);
        assertEquals(volume, 9);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void lanscapeLimitsMaxHeightTest() {
        
        int uncorrectLandscape[] = new int[]{5, 2, 3, 4, 32001, 4, 0, 3, 4};
        
        WaterVolume.calculateWaterAmount(uncorrectLandscape);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void lanscapeLimitsMinHeightTest() {
        
        int uncorrectLandscape[] = new int[]{5, 2, 3, 4, 3, 4, 0, 3, -1};
        
        WaterVolume.calculateWaterAmount(uncorrectLandscape);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void lanscapeLimitsMaxSizeTest() {
        
        int uncorrectLandscape[] = new int[34000];
        Arrays.fill(uncorrectLandscape, 0);
        WaterVolume.calculateWaterAmount(uncorrectLandscape);
    }

    @Test
    public void calculateWaterLevelForwardTest() throws InvocationTargetException {
        // water level per position
        int waterLevel[] = new int[testLandscape.length];
        Arrays.fill(waterLevel, Integer.MAX_VALUE);

        // test findWaterLevelForward()
        Utils.invokeStaticMethod(WaterVolume.class, "findWaterLevelForward", new Class[]{int[].class, int[].class}, new Object[]{testLandscape, waterLevel});
        assertArrayEquals(waterLevel, new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5});
    }

    @Test
    public void calculateWaterLevelBackwardTest() throws InvocationTargetException {
        // water level per position
        int waterLevel[] = new int[testLandscape.length];
        Arrays.fill(waterLevel, Integer.MAX_VALUE);

        // test findWaterLevelForward()
        Utils.invokeStaticMethod(WaterVolume.class, "findWaterLevelBackward", new Class[]{int[].class, int[].class}, new Object[]{testLandscape, waterLevel});
        assertArrayEquals(waterLevel, new int[]{5, 5, 5, 5, 5, 4, 3, 3, 1});
    }

}
