package edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI;

import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;

import java.util.Arrays;

public class OverCookedAI extends AbstractQuestAI {
    private String QuestName= "overcook";

    private int currentOrder=1;
    private long lastOrderTime=0;
    @Override
    public String getQuestName() {
        return QuestName;
    }
    //calculated time for next order.
    public long orderTime(){

        if (currentOrder==1){
            lastOrderTime=(long)(Math.random() * 10000) + 65000;
            return lastOrderTime;
        }
        else{
            //shorter time
            if (questAvgHR <= getMaxTargetHR() && questAvgHR >= getMinTargetHR()) {

                lastOrderTime=(long)(Math.random() * 10000) + 60000;
                return lastOrderTime;

            }
            //High hr ,longer time
            else if (questAvgHR >= getMaxTargetHR()) {
                lastOrderTime=(long)(Math.random() * 10000) + 70000;
                return lastOrderTime;
            }
            //Low hr,shorter time
            else {

                lastOrderTime=(long)(Math.random() * 10000) + 55000;
                return lastOrderTime;
            }
        }

    }
}