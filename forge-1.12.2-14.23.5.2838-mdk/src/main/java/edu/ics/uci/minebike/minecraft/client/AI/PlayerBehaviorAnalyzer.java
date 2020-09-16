package edu.ics.uci.minebike.minecraft.client.AI;

import java.util.ArrayList;
import java.util.Random;

public class PlayerBehaviorAnalyzer {

    private OuterAI outerAI = null;

    public PlayerBehaviorAnalyzer(){
        this.outerAI = OuterAI.getInstance();
    }

    public void findAndSetPopupQuest(){
        ArrayList<String> playedQuests = outerAI.gamePlayTracker.getPlayedQuests();
        ArrayList<AbstractQuestAI> questAIs = QuestAIDatabase.getQuestsAIs();
        if(playedQuests.size() == 0){ // hasn't play any quests
            Random rand = new Random();
            outerAI.setRunningQuest(questAIs.get(rand.nextInt()));
        }else{
            outerAI.setRunningQuest(outerAI.gamePlayTracker.getMaxPlayedQuest());
        }

    }
}
