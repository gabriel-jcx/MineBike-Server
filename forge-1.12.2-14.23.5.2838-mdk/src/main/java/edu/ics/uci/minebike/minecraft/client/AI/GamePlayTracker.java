package edu.ics.uci.minebike.minecraft.client.AI;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.data.Quest;
//import org.json.*;

import java.util.ArrayList;
import java.util.Collections;


// NOTE: the data is stored locally at the client,
//       on Player Quit Game,
public class GamePlayTracker {
    // Game Play is temporarily stored locally.

    private  String name;
    private  int heart_rate;
    private  ArrayList<String> playedQuests = new ArrayList<>();

    public GamePlayTracker(){ // initialize values
        name = "";
        heart_rate = 0;

    }

    public void addPlayedQuest(AbstractCustomQuest quest){
        this.playedQuests.add(quest.getName());
    }

    public ArrayList<String> getPlayedQuests(){
        return playedQuests;
    }

    public String toJsonStr(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public AbstractQuestAI getMaxPlayedQuest(){ // probably better if implemented in HashMap;
        ArrayList<AbstractQuestAI> questAIs = QuestAIDatabase.getQuestsAIs();
        ArrayList<Integer> playedTimes = new ArrayList<Integer>(questAIs.size());
        for(int i = 0; i < playedQuests.size(); i++){
            for(int j = 0; j < questAIs.size(); j++){
                if(playedQuests.get(i).equals(questAIs.get(j).getQuestName())){
                    playedTimes.add(j, 1);
                    break;
                }

            }
        }
        return questAIs.get(Collections.max(playedTimes));
    }

    public static GamePlayTracker toGamePlayData(String jsonData){
        //GamePlayTracker GPT = new GamePlayTracker();
        Gson gson = new Gson();
        return gson.fromJson(jsonData, GamePlayTracker.class);
    }
}
