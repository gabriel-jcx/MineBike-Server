package edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI;

import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;

public class MinerAI extends AbstractQuestAI {
    private String QuestName= "miner";

    @Override
    public String getQuestName() {
        return QuestName;
    }
}
