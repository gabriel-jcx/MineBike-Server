package edu.ics.uci.minebike.minecraft.npcs;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Carissa;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Sam;
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
    public static EntityCustomNpc spawnNpc(BlockPos pos, WorldServer ws, String name,String Texture){
        EntityCustomNpc npc = new EntityCustomNpc((ws));
        npc.display.setName(name);
        npc.display.setSkinTexture(Texture);
        npc.setPosition(pos.getX(),pos.getY(),pos.getZ());
        npc.ais.setStartPos(pos);
        //ws.provider.getDimension().
        ws.spawnEntity(npc);

        // Setting the health to prevent npc being killed
        npc.setHealth(10000f);

        return npc;
    }
    public boolean isNpcSpawned(){

        return false;
    }
    public static ICustomNpc spawnNpc(Vec3d pos, WorldServer ws, World world, String name, String Texture){
        if(world == null ){
            System.out.println("WTF");
        }
//
//        EntityCustomNpc npc = new EntityCustomNpc((world));
//        npc.display.setName(name);
//        npc.display.setVisible(1);
//
//        npc.display.setSkinTexture("customnpcs:textures/entity/humanmale/steve.png");
//        npc.wrappedNPC.setName("ASDF");
//        npc.wrappedNPC.spawn();
//        //npc.setCustomNameTag("jaya");
//        npc.setPosition(pos.getX(),pos.getY(),pos.getZ());
//        npc.setHealth(1000f);
//        System.out.println(name+ " is spwaned at " + "(" + (pos.getX()+2) + "," + pos.getY()+ "," + (pos.getZ()) + ")");
//        System.out.printf("isRemote %b\n", npc.isRemote());
//        //System.out.printf("isInvisibleToPlayer %b\n", npc.isInvisibleToPlayer());
//
//        npc.ais.setStartPos(pos);
//        world.spawnEntity(npc);
        ICustomNpc customNpc = npcAPI.createNPC(world);
        customNpc.getDisplay().setSkinTexture(Texture);
        //customNpc.getDisplay().setOverlayTexture(Texture);
        customNpc.setPosition(pos.x,pos.y,pos.z);

        customNpc.setName(name);
        customNpc.getDisplay().setName(name);
        customNpc.setHealth(1000f);
        customNpc.spawn();

        // = ws.spawnEntity(npc);
        //System.out.printf("Spawning Entity %s was %b\n",name ,status);


        // Setting the health to prevent npc being killed


        return customNpc;
    }
}
