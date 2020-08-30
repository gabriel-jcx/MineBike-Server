package edu.ics.uci.minebike.minecraft.client.AI;

import com.google.gson.JsonObject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.json.*;



// NOTE: the data is stored locally at the client,
//       on Player Quit Game,
public class GamePlayTracker {
    // Game Play is temporarily stored locally.

    private String name;
    private int heart_rate;

    public GamePlayTracker(){

    }


    public JsonObject toJson(){
        JsonObject a = new JsonObject();
        return a;
    }
    public GamePlayTracker toGamePlayData(JsonObject data){
        GamePlayTracker GPT = new GamePlayTracker();
        return GPT;
    }
}
