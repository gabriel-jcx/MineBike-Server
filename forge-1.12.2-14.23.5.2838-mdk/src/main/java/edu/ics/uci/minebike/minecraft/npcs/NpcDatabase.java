package edu.ics.uci.minebike.minecraft.npcs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Jaya;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Sam;
import net.minecraft.util.math.Vec3d;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

public class NpcDatabase {
    public static ArrayList<EntityCustomNpc> npc_entities = new ArrayList<>();
    public static Map<String, Vec3d> npcs = new HashMap<>();
    public static ArrayList<AbstractCustomNpc> customNpcs = new ArrayList<>();
    public NpcDatabase(){
        customNpcs.add(new Sam());
        customNpcs.add(new Jaya());
    }
    public static void registerNpc(String Name, Vec3d Location){
        System.out.printf("Name = "+ Name + "(" + Location.x + "," + Location.y + "," + Location.z + ")");
        npcs.put(Name, Location);
    }
}
