package edu.ics.uci.minebike.minecraft.npcs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Ada;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Jaya;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Renzler;
//import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Sam;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Rinzler;
import noppes.npcs.entity.EntityCustomNpc;

public class NpcDatabase {
    public static ArrayList<EntityCustomNpc> npc_entities = new ArrayList<>();
    public static Map<String, AbstractCustomNpc> npcs = new HashMap<>();
    //public static ArrayList<AbstractCustomNpc> customNpcs = new ArrayList<>();
    public NpcDatabase(){
        npcs.put(Rinzler.NAME,new Rinzler());
        npcs.put(Jaya.NAME,new Jaya());
        npcs.put(Ada.NAME,new Ada());
        npcs.put(Renzler.NAME, new Renzler());
    }
    public static void registerNpcs(){
        //System.out.printf("Name = "+ Name + "(" + Location.x + "," + Location.y + "," + Location.z + ")");
        npcs.put(Jaya.NAME, new Jaya());
        npcs.put(Rinzler.NAME, new Rinzler());
        npcs.put(Ada.NAME,new Ada());
        npcs.put(Renzler.NAME, new Renzler());
    }
}
