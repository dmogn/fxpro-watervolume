package com.github.dmogn.watervolume;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Unit tests of water volume calculation algorithm.
 *
 * @author Dmitry Ognyannikov
 */
public class WaterVolumeTest {

    private int testLandscape[];

    public WaterVolumeTest() {
    }

    @BeforeEach
    public void setUp() {
        testLandscape = new int[]{5, 2, 3, 4, 5, 4, 0, 3, 1};
    }

    @Test
    @DisplayName("general algorithm test")
    public void calculateWaterAmountTest() {
        final long volume = WaterVolume.calculateWaterAmount(testLandscape);
        assertEquals(9, volume);
    }
    
    @Test
    public void lanscapeLimitsMaxHeightTest() {
        
        int uncorrectLandscape[] = new int[]{5, 2, 3, 4, 32001, 4, 0, 3, 4};
        
        assertThrows(IllegalArgumentException.class,
            ()->{
                WaterVolume.calculateWaterAmount(uncorrectLandscape);
            });
    }
    
    @Test
    public void lanscapeLimitsMinHeightTest() {
        
        int uncorrectLandscape[] = new int[]{5, 2, 3, 4, 3, 4, 0, 3, -1};
        
        assertThrows(IllegalArgumentException.class,
            ()->{
                WaterVolume.calculateWaterAmount(uncorrectLandscape);
            });
    }
    
    @Test
    public void lanscapeLimitsMaxSizeTest() {
        int uncorrectLandscape[] = new int[34000];
        Arrays.fill(uncorrectLandscape, 0);
        
        assertThrows(IllegalArgumentException.class,
            ()->{
                WaterVolume.calculateWaterAmount(uncorrectLandscape);
            });
    }

    @Test
    public void calculateWaterLevelForwardTest() throws InvocationTargetException {
        // water level per position
        int waterLevel[] = new int[testLandscape.length];
        Arrays.fill(waterLevel, Integer.MAX_VALUE);

        // test findWaterLevelForward()
        Utils.invokeStaticMethod(WaterVolume.class, "findWaterLevelForward", new Class[]{int[].class, int[].class}, new Object[]{testLandscape, waterLevel});
        assertArrayEquals(new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5}, waterLevel);
    }

    @Test
    public void calculateWaterLevelBackwardTest() throws InvocationTargetException {
        // water level per position
        int waterLevel[] = new int[testLandscape.length];
        Arrays.fill(waterLevel, Integer.MAX_VALUE);

        // test findWaterLevelForward()
        Utils.invokeStaticMethod(WaterVolume.class, "findWaterLevelBackward", new Class[]{int[].class, int[].class}, new Object[]{testLandscape, waterLevel});
        assertArrayEquals(new int[]{5, 5, 5, 5, 5, 4, 3, 3, 1}, waterLevel);
    }

}
