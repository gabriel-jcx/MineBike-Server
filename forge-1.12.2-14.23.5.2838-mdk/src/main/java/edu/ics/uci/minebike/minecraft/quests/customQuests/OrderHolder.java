package edu.ics.uci.minebike.minecraft.quests.customQuests;


import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;

import java.awt.*;
import java.util.ArrayList;

public class OrderHolder {

    private ArrayList<Recipe> foods = new ArrayList<>();
    private ArrayList<Long> startTimes = new ArrayList<>();
    private ArrayList<Long> expiration = new ArrayList<>();
    private ArrayList<HudString> timer = new ArrayList<>();
    private HudString orderHolderTitle;
    private long lastUpdate;
    private int complete = 30;
    private int expired = -10;
    OrderHolder(){
        orderHolderTitle = new HudString(-200,20,"Order List:",2.5f, true, false);
        lastUpdate = System.currentTimeMillis();
    }

    public void add(Recipe newFood, long timeLimit){
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

    public ArrayList<Recipe> getFoods(){return foods;}

    public int size(){return foods.size();}

    public void update(long curTime){
        if(curTime - lastUpdate < 1000) {
            return;
        }else{
            lastUpdate = curTime;
        }
        int cur = 0;
        for(HudString time : timer){
            time.text = foods.get(cur).getName() + "  Time Left: " + QuestUtils.getRemainingSeconds(expiration.get(cur), curTime);
            if(expiration.get(cur) - curTime < 10000)
            {
                time.color = 0x00ffffff;
            }
            cur++;
        }
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

    public void expire(int ind){
        for(int i = 0 ; i < expiration.size(); i++){
            if(i > ind){
                timer.get(i).y = timer.get(i-1).y;
            }
        }
        timer.get(ind).unregister();
        timer.remove(ind);
        expiration.remove(ind);
        startTimes.remove(ind);
        System.out.println(foods.remove(ind).getName() + " Has Expired");
    }

    public void complete(int ind){
        for(int i = 0 ; i < expiration.size(); i++){
            if(i > ind){
                timer.get(i).y = timer.get(i-1).y;
            }
        }
        timer.get(ind).unregister();
        timer.remove(ind);
        expiration.remove(ind);
        startTimes.remove(ind);
        System.out.println(foods.remove(ind).getName() + " Has Been Completed");
    }
}
