package edu.ics.uci.minebike.minecraft.client.AI;

import edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI.FishingAI;
import edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI.MinerAI;
import edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI.OverCookedAI;
import edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.Minequest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.OverCookedQuest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.SoccerQuest;

import java.util.ArrayList;
import java.util.Set;


// TODO: make the questAIs into a hash-map?
public class QuestAIDatabase {
    private static ArrayList<AbstractQuestAI> questsAIs  = new ArrayList<>();

//    private static ArrayList<Set<AbstractQuestAI,String>>

    public QuestAIDatabase(){
        questsAIs.add(new OverCookedAI());
        questsAIs.add(new FishingAI());
        questsAIs.add(new MinerAI());

    }
    public static ArrayList<AbstractQuestAI> getQuestsAIs(){ return questsAIs;}
    public static void addQuestAI(AbstractQuestAI questAI){
        questsAIs.add(questAI);
    }

}
