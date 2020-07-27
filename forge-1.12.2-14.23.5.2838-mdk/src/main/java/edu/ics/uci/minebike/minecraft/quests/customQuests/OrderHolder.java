package edu.ics.uci.minebike.minecraft.quests.customQuests;


import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;

import java.util.ArrayList;

public class OrderHolder {

    private ArrayList<Recipe> foods = new ArrayList<>();
    private ArrayList<Long> startTimes = new ArrayList<>();
    private ArrayList<Long> expiration = new ArrayList<>();
    private ArrayList<HudString> timer = new ArrayList<>();
    private HudString orderHolderTitle;
    private long timeLimit;
    OrderHolder(){
        timeLimit = 30000;
        orderHolderTitle = new HudString(-100,25,"Order List:", true, false);
    }

    public void add(Recipe newFood){
        foods.add(newFood);
        long curTime = System.currentTimeMillis();
        long endTime = curTime + timeLimit;
        startTimes.add(curTime);
        expiration.add(endTime);
        timer.add(new HudString(-100,30 + timer.size() * 5,newFood.getName() + "\nTime Left: " + QuestUtils.getRemainingSeconds(endTime,curTime),true, false));
    }

    public Recipe remove(int ind){
        startTimes.remove(ind);
        expiration.remove(ind);
        timer.get(ind).unregister();
        return foods.remove(ind);
    }

    public Recipe get(int ind){
        return foods.get(ind);
    }

    public int size(){return foods.size();}

    public void update(){
        int cur = 0;
        for(HudString time : timer){
            time.text = foods.get(cur).getName() + "\nTime Left: " + QuestUtils.getRemainingSeconds(expiration.get(0),startTimes.get(0));
            cur++;
        }
    }

    public void endGame() {
        for (HudString time : timer) {
            time.unregister();
        }
        while(foods.size() != 0){
            foods.remove(0);
        }
    }
}
