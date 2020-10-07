package edu.ics.uci.minebike.minecraft.client.AI;

import java.util.ArrayList;
import java.util.Random;

public class PlayerBehaviorAnalyzer {

    private OuterAI outerAI = OuterAI.getInstance();


    public PlayerBehaviorAnalyzer(){

    }

    public void findAndSetPopupQuest(){
        ArrayList<String> playedQuests = outerAI.gamePlayTracker.getPlayedQuests();
        ArrayList<AbstractQuestAI> questAIs = QuestAIDatabase.getQuestsAIs();
        if(playedQuests.size() == 0){ // hasn't play any quests
            System.out.println("playedQuests.size() == 0");
            Random rand = new Random();
            AbstractQuestAI temp= questAIs.get(rand.nextInt(questAIs.size()));
            System.out.println(temp.getQuestName()+temp.getQuestDim());
            outerAI.setRunningQuest(temp);
        }else{
            System.out.println("playedQuests.size() !!!!= 0");
            outerAI.setRunningQuest(outerAI.gamePlayTracker.getMaxPlayedQuest());
        }

    }
}
