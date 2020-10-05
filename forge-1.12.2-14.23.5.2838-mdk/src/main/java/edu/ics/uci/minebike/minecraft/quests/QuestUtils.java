package edu.ics.uci.minebike.minecraft.quests;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class QuestUtils {
    public static int getRemainingSeconds(long curr, long start){
        return (int)((curr - start) /1000);
    }

    public static int getRemainingSeconds(long curr){
        return (int)((curr) /1000);
    }
    public static int getSeconds(long curr){
        return (int)(curr/1000);
    }

    public static String formatSeconds(int timeLeft){
        int secondsLeft = timeLeft%60;
        return (Integer.toString(timeLeft/60) + ": " + ((secondsLeft < 10) ? "0":"") + Integer.toString(secondsLeft));
    }

    // temporary list populate
    // TODO: find the locations and add manually
    public static void populateSoccerPlayerLocations(ArrayList<BlockPos> playerSpawnList, int max_size){
        for(int i = 0; i < max_size; ++ i)
            playerSpawnList.add(new BlockPos(10,10,10));
    }
}
