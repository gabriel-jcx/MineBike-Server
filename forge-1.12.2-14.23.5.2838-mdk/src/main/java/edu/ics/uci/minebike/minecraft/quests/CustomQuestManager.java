package edu.ics.uci.minebike.minecraft.quests;

import java.util.ArrayList;

public class CustomQuestManager {
    public static ArrayList<AbstractCustomQuest> quest_list = new ArrayList<>();
    public CustomQuestManager(){
        quest_list.add(new SoccerQuest());
    }
}
