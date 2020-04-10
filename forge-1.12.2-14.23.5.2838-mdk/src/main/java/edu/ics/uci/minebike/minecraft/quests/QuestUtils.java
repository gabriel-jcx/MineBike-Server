package edu.ics.uci.minebike.minecraft.quests;

public class QuestUtils {
    public static int getRemainingSeconds(long curr, long start){
        return (int)((curr - start) /1000);
    }
    public static String formatSeconds(int timeLeft){
        int secondsLeft = timeLeft%60;
        return (Integer.toString(timeLeft/60) + ": " + ((secondsLeft < 10) ? "0":"") + Integer.toString(secondsLeft));
    }
}
