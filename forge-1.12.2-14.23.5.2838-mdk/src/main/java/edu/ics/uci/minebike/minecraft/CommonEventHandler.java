package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.npcs.NpcUtils;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noppes.npcs.entity.EntityCustomNpc;

import java.util.Map;

public class CommonEventHandler {
    NpcDatabase npcDatabase = new NpcDatabase();
    public CommonEventHandler(){


    }
    public void spawn(){
        spawnNpcDatabase();
    }


    public void spawnNpcDatabase(){
        WorldServer ws = DimensionManager.getWorld(0);
        WorldServer ws1 = DimensionManager.getWorlds()[0];
        System.out.println("World Server get");
        if(NpcDatabase.npcs.size() != 0) {
            for (Map.Entry<String, Vec3d> npc : NpcDatabase.npcs.entrySet()) {
                System.out.println(npc.getKey() + " is spwaned at " + "(" + npc.getValue().x + "," + npc.getValue().y + "," + npc.getValue().z + ")");
                NpcUtils.spawnNpc(new BlockPos(npc.getValue()), ws1, npc.getKey());
                System.out.println(npc.getKey() + " is spwaned at " + "(" + npc.getValue().x + "," + npc.getValue().y + "," + npc.getValue().z + ")");
            }
        }
    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){
//        System.out.println("Player tick event triggered");
//        spawnNpcDatabase();
    }
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event ){
        //event.getWorld().provider
        System.out.println("A world is loaded, WorldEvent.Load triggerd");
        spawnNpcDatabase();
    }
}
