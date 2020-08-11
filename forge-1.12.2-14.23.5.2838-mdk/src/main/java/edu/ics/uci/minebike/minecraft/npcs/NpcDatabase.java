package edu.ics.uci.minebike.minecraft.npcs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.*;
import noppes.npcs.entity.EntityCustomNpc;

public class NpcDatabase {
    public static ArrayList<EntityCustomNpc> npc_entities = new ArrayList<>();
    public static Map<String, AbstractCustomNpc> npcs = new HashMap<>();
    //public static ArrayList<AbstractCustomNpc> customNpcs = new ArrayList<>();
    public NpcDatabase(){
        npcs.put(Sam.NAME,new Sam());
        npcs.put(Jaya.NAME,new Jaya());
        npcs.put(Ada.NAME,new Ada());
        npcs.put(ChefGusteau.NAME, new ChefGusteau());
        npcs.put(Gordon.NAME, new Gordon());
        npcs.put(Shuttle.NAME, new Shuttle());
        npcs.put(Manager.NAME, new Manager());
        npcs.put(Waiter.NAME, new Waiter());
    }
    public static void registerNpcs(){
        //System.out.printf("Name = "+ Name + "(" + Location.x + "," + Location.y + "," + Location.z + ")");
        npcs.put(Jaya.NAME, new Jaya());
        npcs.put(Sam.NAME, new Sam());
        npcs.put(Ada.NAME,new Ada());
        npcs.put(ChefGusteau.NAME,new ChefGusteau());
        npcs.put(Gordon.NAME, new Gordon());
        npcs.put(Shuttle.NAME, new Shuttle());
        npcs.put(Manager.NAME, new Manager());
        npcs.put(Waiter.NAME, new Waiter());
    }
}
