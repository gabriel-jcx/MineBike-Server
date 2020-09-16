package edu.ics.uci.minebike.minecraft.npcs;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Carissa;
//import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Sam;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import noppes.npcs.NPCSpawning;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.api.NpcAPI;

public class NpcUtils{
    public static NpcAPI npcAPI = NpcAPI.Instance();
//    public static EntityCustomNpc spawnNpc(BlockPos pos, WorldServer ws, String name,String Texture){
//        EntityCustomNpc npc = new EntityCustomNpc((ws));
//        npc.display.setName(name);
//        npc.display.setSkinTexture(Texture);
//        npc.setPosition(pos.getX(),pos.getY(),pos.getZ());
//        npc.ais.setStartPos(pos);
//        //ws.provider.getDimension().
//        ws.spawnEntity(npc);
//
//        // Setting the health to prevent npc being killed
//        npc.setHealth(10000f);
//
//        return npc;
//    }
    public boolean isNpcSpawned(World worldIn, EntityCustomNpc npc){ // doesn't work correctly
        if (worldIn.loadedEntityList.contains(npc))
            return true;
        return false;
    }
    public static EntityCustomNpc spawnNpc(Vec3d pos, WorldServer ws, String name, String Texture){
        EntityCustomNpc npc = new EntityCustomNpc(ws);
        npc.wrappedNPC.setPosition(pos.x,pos.y,pos.z);
        npc.wrappedNPC.getDisplay().setName(name);
        npc.wrappedNPC.setHealth(1000f);
        npc.wrappedNPC.spawn();

        //npc.delete();
        return npc;
    }
    public static EntityCustomNpc spawnNpc(Vec3d pos, WorldServer ws, World world, String name, String Texture){
        if(world == null ){
            System.out.println("Wordl is null, should not spawn ");
            return null;
        }
        EntityCustomNpc npc = new EntityCustomNpc(world);
        npc.wrappedNPC.getDisplay().setSkinTexture(Texture);
        npc.wrappedNPC.setPosition(pos.x,pos.y,pos.z);
        npc.wrappedNPC.getDisplay().setName(name);
        npc.wrappedNPC.setHealth(1000f);
        npc.wrappedNPC.spawn();

        return npc;
    }
    public static boolean removeNpc(ICustomNpc npc, World worldIn, String name){

        return true;
    }
}
