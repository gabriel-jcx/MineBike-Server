package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.npcs.NpcEventHandler;
import edu.ics.uci.minebike.minecraft.npcs.NpcUtils;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Jaya;
import edu.ics.uci.minebike.minecraft.quests.SoccerQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import noppes.npcs.api.NpcAPI;
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
        //if(NpcDatabase.npcs.size() != 0) {
            for (Map.Entry<String,AbstractCustomNpc> iter: NpcDatabase.npcs.entrySet()) {
                AbstractCustomNpc npc = iter.getValue();
                EntityCustomNpc npcEntity = NpcUtils.spawnNpc(npc.getLocation(), ws,worldIn,npc.getName(), npc.getTexture());
                NpcDatabase.npc_entities.add(npcEntity);
                BlockPos location = npcEntity.getPosition();
                npc.setUUID(npcEntity.getUniqueID().toString());
                //BlockPos pos = temp_npc.getPos();
                System.out.println(npc.getName() + " is spwaned at " + location);
                //System.out.println(npc.getKey() + " is spwaned at " + "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ()+ ")");
            }
        //}
    }
    public void spawnCustomClient(){
        NpcDatabase.registerNpcs();
    }
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        EntityPlayer player = event.getEntityPlayer();
        if(event.getTarget() instanceof EntityCustomNpc) {
            NpcEventHandler.customNpcInteract(player, event);
        }
    }
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event){
        System.out.println(event.getEntity().getName() + " is spawned at DIM" + event.getWorld().provider.getDimension());
        if(!event.getWorld().isRemote && event.getEntity() instanceof  EntityPlayer && event.getWorld().provider.getDimension() == WorldProviderSoccerQuest.DIM_ID){
            System.out.println("inside the fi statement!!!!");
            NpcAPI.Instance().spawnNPC(event.getWorld(),10, 5,10 );
//            NpcUtils.spawnNpc(new Vec3d(10,5,10),DimensionManager.getWorld(event.getWorld().provider.getDimension()), event.getWorld(),"Cole", "customnpcs:textures/entity/humanmale/kingsteve.png");
        }

    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(!spawned){
            World w = event.player.getEntityWorld();
            if(!event.player.getEntityWorld().isRemote){
                spawnNpcDatabase(w.provider.getDimension(),w.getSpawnPoint(),w); // server spawn the npcs
            }else {
                spawnCustomClient();
            }

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

//        if(!event.getWorld().isRemote){// if Running on the server
//            System.out.println("World Loaded on Server!!!!!");
////            spawnNpcDatabase(event.getWorld().provider.getDimension(),event.getWorld().getSpawnPoint());
//        }

//          if(event.getWorld().provider.getDimension() == 222){
//              EntityCustomNpc tempnpc = NpcUtils.spawnNpc(new Vec3d(11,10,11),event.getWorld(),Jaya.NAME, Jaya.TEXTURE_NAME);
//          }
// TODO: comment the follow section, need to figure out if it actually spawns the npc.
//          if(event.getWorld().provider.getDimension() == 222){
//              EntityCustomNpc tempnpc = NpcUtils.spawnNpc(new Vec3d(11,10,11),event.getWorld(),Jaya.NAME, Jaya.TEXTURE_NAME);
//          }
//          if(event.getWorld().provider.getDimension() == 223){
//            EntityCustomNpc tempnpc = NpcUtils.spawnNpc(new Vec3d(100,10,100),event.getWorld(),Jaya.NAME, Jaya.TEXTURE_NAME);
//          }
    }
//    @SubscribeEvent
//    public void onNpcInit(NpcEvent.InitEvent event){
//        System.out.printf("Npc Spawned!!!");
//    }

    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile event){
        System.out.println(event.getEntityPlayer().getName() + " is trying to SaveToFile");
    }
//
//    @SubscribeEvent
//    public void onIDK(TickE)
}
