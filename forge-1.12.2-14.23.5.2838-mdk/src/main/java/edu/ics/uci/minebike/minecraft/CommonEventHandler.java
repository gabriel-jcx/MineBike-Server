package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.npcs.NpcEventHandler;
import edu.ics.uci.minebike.minecraft.npcs.NpcUtils;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noppes.npcs.api.IPos;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.entity.EntityCustomNpc;

import java.util.Map;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber
public class CommonEventHandler {
    NpcDatabase npcDatabase = new NpcDatabase();
    public static boolean spawned = false;
    public CommonEventHandler(){

    }
    public void spawn(){
        //spawnNpcDatabase();
    }


    public void spawnNpcDatabase(int worldId, BlockPos pos, World worldIn){

        WorldServer ws = DimensionManager.getWorld(worldId);

        System.out.printf("Number of Worlds = %s\n", DimensionManager.getWorlds().length);
        System.out.println("World Server get");
        if(NpcDatabase.npcs.size() != 0) {
            for (AbstractCustomNpc npc: NpcDatabase.customNpcs) {
                EntityCustomNpc npcEntity = NpcUtils.spawnNpc(npc.getLocation(), ws,worldIn,npc.getName(), npc.getTexture());
                NpcDatabase.npc_entities.add(npcEntity);
                BlockPos location = npcEntity.getPosition();
                npc.setUUID(npcEntity.getUniqueID().toString());
                //BlockPos pos = temp_npc.getPos();
                System.out.println(npc.getName() + " is spwaned at " + "(" + location.getX() + "," + location.getY() + "," + location.getZ() + ")");
                //System.out.println(npc.getKey() + " is spwaned at " + "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ()+ ")");
            }
        }
    }
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        EntityPlayer player = event.getEntityPlayer();
        NpcEventHandler.customNpcInteract(player, event);
    }
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event){

    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(!spawned && !event.player.getEntityWorld().isRemote){
            World w = event.player.getEntityWorld();
            spawnNpcDatabase(w.provider.getDimension(),w.getSpawnPoint(),w);
            spawned = true;
        }
//        for(EntityCustomNpc npc: NpcDatabase.npc_entities){
//            System.out.println(npc.getName() + " is dead ? " +npc.isDead);
//        }
//        System.out.println("Player tick event triggered");
//        spawnNpcDatabase();
    }
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event ){
        System.out.println("A world is loaded, WorldEvent.Load triggerd");

        if(!event.getWorld().isRemote){// if Running on the server
            System.out.println("World Loaded on Server!!!!!");
//            spawnNpcDatabase(event.getWorld().provider.getDimension(),event.getWorld().getSpawnPoint());

        }

    }
    @SubscribeEvent
    public void onNpcInit(NpcEvent.InitEvent event){
        System.out.printf("Npc Spawned!!!");
    }

    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile event){
        System.out.println(event.getEntityPlayer().getName() + " is trying to SaveToFile");
    }
//
//    @SubscribeEvent
//    public void onIDK(TickE)
}
