package edu.ics.uci.minebike.minecraft.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


// To register a custom
public class CustomQuestManager {


    public static Map<String, AbstractCustomQuest> customQuests = new HashMap<String, AbstractCustomQuest>();
    public static ArrayList<Integer> questDimsIDs = new ArrayList<>();

    public CustomQuestManager() {

        customQuests.put("soccer", new SoccerQuest());

        customQuests.put("fishing", new FishingQuest());

    }


}
