package edu.ics.uci.minebike.minecraft.npcs;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import noppes.npcs.entity.EntityCustomNpc;

public class NpcUtils {

    public static EntityCustomNpc spawnNpc(BlockPos pos, WorldServer ws, String name,String Texture){
        EntityCustomNpc npc = new EntityCustomNpc((ws));
        npc.display.setName(name);
        npc.display.setSkinTexture(Texture);
        npc.setPosition(pos.getX(),pos.getY(),pos.getZ());
        npc.ais.setStartPos(pos);
        ws.spawnEntity(npc);

        // Setting the health to prevent npc being killed
        npc.setHealth(10000f);

        return npc;
    }
    public static EntityCustomNpc spawnNpc(BlockPos pos, WorldServer ws, String name){
        if(ws == null ){
            System.out.println("WTF");
        }
        EntityCustomNpc npc = new EntityCustomNpc((ws));
        npc.display.setName(name);
        npc.setPosition(pos.getX(),pos.getY(),pos.getZ());
        npc.ais.setStartPos(pos);
        ws.spawnEntity(npc);

        // Setting the health to prevent npc being killed
        npc.setHealth(10000f);

        return npc;
    }
}
