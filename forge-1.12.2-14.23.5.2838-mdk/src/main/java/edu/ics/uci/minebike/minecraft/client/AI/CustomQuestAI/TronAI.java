package edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI;

import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;

public class TronAI extends AbstractQuestAI {
    private String QuestName= "tron";

    private int QuestDim=250;
    @Override
    public int getQuestDim() {
        return QuestDim;
    }
    @Override
    public String getQuestName() {
        return QuestName;
    }
}