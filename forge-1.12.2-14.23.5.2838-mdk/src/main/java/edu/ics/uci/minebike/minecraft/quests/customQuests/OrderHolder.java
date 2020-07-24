package edu.ics.uci.minebike.minecraft.quests.customQuests;


import edu.ics.uci.minebike.minecraft.client.hud.HudString;

import java.util.ArrayList;

public class OrderHolder {

    private ArrayList<Recipe> foods = new ArrayList<>();
    private ArrayList<Long> startTimes = new ArrayList<>();
    private ArrayList<Long> expiration = new ArrayList<>();
    private ArrayList<HudString> timer = new ArrayList<>();
    private long timeLimit;
    private OrderHolder(){
        timeLimit = 30000;
    }

    public void add(Recipe newFood){
        foods.add(newFood);
        startTimes.add(System.currentTimeMillis());
        expiration.add(System.currentTimeMillis() + timeLimit);
    }

    public Recipe remove(int ind){
        startTimes.remove(ind);
        expiration.remove(ind);
        return foods.remove(ind);
    }

    public Recipe get(int ind){
        return foods.get(ind);
    }

    public int size(){return foods.size();}
}
