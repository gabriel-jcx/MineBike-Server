package edu.ics.uci.minebike.minecraft.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MineBikeScheduler {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void runTask(Runnable task, int delay){
        executor.schedule(task,(long)delay, TimeUnit.MILLISECONDS);
    }
    public static void runTask(Runnable task){
        executor.schedule(task, 0L, TimeUnit.MILLISECONDS);
    }
}
