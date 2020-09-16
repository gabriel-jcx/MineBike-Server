package edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI;

import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;
import edu.ics.uci.minebike.minecraft.client.AI.OuterAI;

import java.util.ArrayList;
import java.util.Map;

public class MinerAI extends AbstractQuestAI {
    private String QuestName= "Miner";
    private OuterAI outerAI= OuterAI.getInstance();
    private ArrayList<Integer> timeUsedForEachLane = new ArrayList<Integer>();
    @Override
    public String getQuestName() {
        return QuestName;
    }

    private int calcTimeForNextLane(){
        return 0;
    };
    public int getTimeForNextLane(){
        return 0;
    };
    private void getLastLaneTime(int time){
        timeUsedForEachLane.add(time);

    }
    //Todo: maybe need to check whether user has finished a lane.
    //public void onPlayerTick(TickEvent.PlayerTickEvent event) {}

}
