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
    private int complete = 30;
    private int expired = -10;
    OrderHolder(){
        timeLimit = 30000;
        orderHolderTitle = new HudString(-250,20,"Order List:",2.0f, true, false);
    }

    public void add(Recipe newFood){
        foods.add(newFood);
        long curTime = System.currentTimeMillis();
        long endTime = curTime + timeLimit;
        startTimes.add(curTime);
        expiration.add(endTime);
        timer.add(new HudString(-200,35 + timer.size() * 10,newFood.getName() + "  Time Left: " + QuestUtils.getRemainingSeconds(endTime,curTime),true, false));
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

    public int update(){
        long curTime = System.currentTimeMillis();
        int output = 0;
        int cur = 0;
        for(HudString time : timer){
            if(curTime < expiration.get(cur)) {
                time.text = foods.get(cur).getName() + "  Time Left: " + QuestUtils.getRemainingSeconds(expiration.get(cur), curTime);
            }
            else{
                output += expired;
            }
            cur++;
        }
        return output;
    }

    public void endGame() {
        orderHolderTitle.unregister();
        for (HudString time : timer) {
            time.unregister();
        }
        foods.clear();
        timer.clear();
        expiration.clear();
        startTimes.clear();
    }

    public ArrayList<Long> getExpiration(){return expiration;}
}
