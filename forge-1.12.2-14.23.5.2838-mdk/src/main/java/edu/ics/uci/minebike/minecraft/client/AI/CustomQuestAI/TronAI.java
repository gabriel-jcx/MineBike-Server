package edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI;

import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;

import java.util.Arrays;

public class TronAI extends AbstractQuestAI {
    private String QuestName= "Tron";

    @Override
    public String getQuestName() {
        return QuestName;
    }

    public int calcSpeed(int cur){
        if (questAvgHR <= getMaxTargetHR() && questAvgHR >= getMinTargetHR()) {
            return cur+1;
        }
        //High hr ,smaller fish
        else if (questAvgHR >= getMaxTargetHR()) {
           return cur-2;
        }
        //Low hr ,bigger fish
        else {
           return cur+2;
        }
    }
}