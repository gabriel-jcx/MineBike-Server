package edu.ics.uci.minebike.minecraft.client.AI;

import java.util.ArrayList;

public class QuestDatabase {
    private static ArrayList<AbstractQuestAI> questsAIs  = new ArrayList<>();


    public static ArrayList<AbstractQuestAI> getQuestsAIs(){ return questsAIs;}

}
