package edu.ics.uci.minebike.minecraft.quests;

import edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.Minequest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.OverCookedQuest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.SoccerQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderOverCooked;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;


// To register a custom
public class CustomQuestManager {

    // Custom Quests is a container to map the Dimension ID associated with each custom Quest;
    public static Map<Integer, AbstractCustomQuest> customQuests = new HashMap<>();
    //public static ArrayList<Integer> questDimsIDs = new ArrayList<>();

    public CustomQuestManager() {
        // NOTE: Custom Quest Initalizer
        customQuests.put(WorldProviderSoccerQuest.DIM_ID, new SoccerQuest());
        //questDimsIDs.add(WorldProviderSoccerQuest.DIM_ID);
        customQuests.put(WorldProviderFishing.DIM_ID, new FishingQuest());
        //questDimsIDs.add(WorldProviderFishing.DIM_ID);
        customQuests.put(WorldProviderMiner.DIM_ID, new Minequest());
        customQuests.put(WorldProviderOverCooked.DIM_ID, new OverCookedQuest());



    }

    public static void findAndStart(EntityJoinWorldEvent event){

        AbstractCustomQuest quest = customQuests.get(event.getWorld().provider.getDimension());
        if(quest != null) {
            // Do nothing for Player joining non-quest dimensions
            quest.start(event);
        }
        return;
    }
    // NOTE: findAndStart only happens on client side at the moment
    public static void findAndStart(int dimID){
        //System.out.println("Finding Quest with dimID = " + dimID);
        AbstractCustomQuest quest = customQuests.get(dimID);
        System.out.println(quest);
        if(quest != null){
            quest.start(Minecraft.getMinecraft().player);
            quest.start();
        }else{
            System.out.println("Unable to find quest with DIMID = " + dimID);
        }
    }
    public static void onWorldTick(TickEvent.WorldTickEvent event){
        AbstractCustomQuest quest = customQuests.get(event.world.provider.getDimension());
        if(quest != null){
            quest.onWorldTick(event);
        }
        return;
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        AbstractCustomQuest quest = customQuests.get(event.player.dimension);
        if(quest != null){
            quest.onPlayerTick(event);
        }
        return;
    }
}
