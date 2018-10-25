package com.github.dmogn.watervolume;

import java.util.Arrays;

/**
 * Volume of water calculation.
 * 
 * @author Dmitry Ognyannikov
 */
public class WaterVolume {
    public static final int HEIGHT_MAX = 32000;
    public static final int HEIGHT_MIN = 0;
    public static final int LANDSCAPE_SIZE_MAX = 32000;
    
    public static long calculateWaterAmount(int[] landscape) {
        //  validate correctness of landscape
        if (landscape.length > LANDSCAPE_SIZE_MAX)
            throw new IllegalArgumentException("Landscape size cannot be more then LANDSCAPE_SIZE_MAX");
        
        for (int height : landscape) {
            if (height > HEIGHT_MAX)
                throw new IllegalArgumentException("Landscape height cannot be more then HEIGHT_MAX");
            if (height < HEIGHT_MIN)
                throw new IllegalArgumentException("Landscape height cannot be lower then HEIGHT_MIN");
        }
            
        // water level per position
        int waterLevel[] = new int[landscape.length];
        Arrays.fill(waterLevel, Integer.MAX_VALUE);
        
        // step 1: Compare and find minimal water level position from peak to peak from both sides
        
        // step 1.1: Find top water peaks from first position to last. Mark water level position
        findWaterLevelForward(landscape, waterLevel);
        
        // step 1.2: Find top water peaks from last position to first. Mark water level position
        findWaterLevelBackward(landscape, waterLevel);
        
        // step 2: Find water volume per position and total water volume
        long amount = 0;
        for (int i=0; i<landscape.length; i++) {
            amount += waterLevel[i] - landscape[i];
        }
        
        return amount;
    }
    
    private static void findWaterLevelForward(int[] landscape, int[] waterLevel) {
        int lastTopHeight = landscape[0];
        
        for (int i=0; i<landscape.length; i++) {
            final int height = landscape[i];
            
            if (height > lastTopHeight) {
                lastTopHeight = height;
            }
            
            if (lastTopHeight < waterLevel[i])
                waterLevel[i] = lastTopHeight;
        }
    }
    
    private static void findWaterLevelBackward(int[] landscape, int waterLevel[]) {
        int lastTopHeight = landscape[landscape.length-1];
        
        for (int i=landscape.length-1; i>=0; i--) {
            final int height = landscape[i];
            
            if (height > lastTopHeight) {
                lastTopHeight = height;
            }
            
            if (lastTopHeight < waterLevel[i])
                waterLevel[i] = lastTopHeight;
        }        
    }
}
