package edu.ics.uci.minebike.minecraft.npcs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Jaya;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Sam;
import net.minecraft.util.math.Vec3d;
import noppes.npcs.entity.EntityCustomNpc;

public class NpcDatabase {
    public static Map<String, EntityCustomNpc> npc_entities;
    public static Map<String, Vec3d> npcs = new HashMap<>();
    public static ArrayList<AbstractCustomNpc> customNups = new ArrayList<>();
    public NpcDatabase(){
        customNups.add(new Sam());
        customNups.add(new Jaya());
    }
    public static void registerNpc(String Name, Vec3d Location){
        System.out.printf("Name = "+ Name + "(" + Location.x + "," + Location.y + "," + Location.z + ")");
        npcs.put(Name, Location);
    }
}
