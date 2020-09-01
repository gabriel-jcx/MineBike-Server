package edu.ics.uci.minebike.minecraft.client.AI;

import java.util.ArrayList;
import java.util.Set;


// TODO: make the questAIs into a hash-map?
public class QuestAIDatabase {
    private static ArrayList<AbstractQuestAI> questsAIs  = new ArrayList<>();

//    private static ArrayList<Set<AbstractQuestAI,String>>

    public static ArrayList<AbstractQuestAI> getQuestsAIs(){ return questsAIs;}
    public static void addQuestAI(AbstractQuestAI questAI){
        questsAIs.add(questAI);
    }

}
