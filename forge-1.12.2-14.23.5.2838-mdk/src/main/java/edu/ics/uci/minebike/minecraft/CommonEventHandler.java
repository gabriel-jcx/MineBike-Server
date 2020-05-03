package edu.ics.uci.minebike.minecraft;

import com.sun.media.jfxmedia.events.PlayerStateEvent;
import edu.ics.uci.minebike.minecraft.item.ItemManager;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.npcs.NpcEventHandler;
import edu.ics.uci.minebike.minecraft.npcs.NpcUtils;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.SoccerQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import noppes.npcs.entity.EntityCustomNpc;

import java.util.Iterator;
import java.util.Map;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber
public class CommonEventHandler {
    NpcDatabase npcDatabase = new NpcDatabase();
    public static boolean spawned = false;
    public static boolean loaded = false;
    public static boolean success = false;
    public CommonEventHandler(){

    }
    public void spawn(){
        //spawnNpcDatabase();
    }
//    @SubscribeEvent
//    public static void onRegisterModel(ModelRegistryEvent e) {
//        ItemManager.registerModels();
//    }

    public void spawnNpcDatabase(int worldId, BlockPos pos, World worldIn){

        WorldServer ws = DimensionManager.getWorld(worldId);

        System.out.printf("Number of Worlds = %s\n", DimensionManager.getWorlds().length);
        System.out.println("World Server get");
        //if(NpcDatabase.npcs.size() != 0) {
        for (Map.Entry<String,AbstractCustomNpc> iter: NpcDatabase.npcs.entrySet()) {
            AbstractCustomNpc npc = iter.getValue();
            if(npc.isSpawned) continue;
            EntityCustomNpc npcEntity = NpcUtils.spawnNpc(npc.getLocation(), ws,worldIn,npc.getName(), npc.getTexture());
            //NpcDatabase.npc_entities.add(npcEntity);
            BlockPos location = npcEntity.getPosition();
            npc.setUUID(npcEntity.getUniqueID().toString());
            //BlockPos pos = temp_npc.getPos();
            System.out.println(npc.getName() + " is spwaned at " + location);
            //System.out.println(npc.getKey() + " is spwaned at " + "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ()+ ")");
        }
    }
    private void spawnCustomClient(){
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
    public void onWorldTick(TickEvent.WorldTickEvent event){
        if(event.world.provider.getDimension() != 0){ // if the world is not overall world
            CustomQuestManager.onWorldTick(event);
        }
    }
//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent event){
//
//    }
    @SubscribeEvent
    public void onPlayerChangeDim(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event){
        System.out.println(event.player.getName() + " changed from DIM"+ event.fromDim + " to " + event.toDim);
        if(event.fromDim == WorldProviderSoccerQuest.DIM_ID ){
            System.out.println(event.player.getName() + " is leaving Soccer");
            SoccerQuest soccer = (SoccerQuest)CustomQuestManager.customQuests.get(222);
            if(soccer.playersInGame.size() == 0){
                // forcing Soccer to end
                soccer.end();
            }
//            soccer.end();
        }
    }
    @SubscribeEvent
    public void onPlayerLogout(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event){
        System.out.println(event.player.dimension);
        System.out.println(event.player.getName() + " has logged out");

        if(event.player.world.provider.getDimension() == WorldProviderSoccerQuest.DIM_ID){
            SoccerQuest soccer = (SoccerQuest)CustomQuestManager.customQuests.get(222);
            soccer.playersInGame.remove((EntityPlayerMP)event.player);
            if(soccer.playersInGame.isEmpty()){
                soccer.end();
            }
        }

    }
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event){

        //System.out.println(event.getEntity().getName() + " is spawned at DIM" + event.getWorld().provider.getDimension());
        if(!event.getWorld().isRemote && (event.getEntity() instanceof EntityPlayer)){
//            CustomQuestManager.findAndStart(event);
        }


//        if(!event.getWorld().isRemote && event.getEntity() instanceof  EntityPlayer && event.getWorld().provider.getDimension() == WorldProviderSoccerQuest.DIM_ID){
//            System.out.println("inside the fi statement!!!!");
//
////            NpcUtils.spawnNpc(new Vec3d(10,5,10),DimensionManager.getWorld(event.getWorld().provider.getDimension()), event.getWorld(),"Cole", "customnpcs:textures/entity/humanmale/kingsteve.png");
//        }
    }


    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(!spawned && event.player instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.player;

            World w = player.getEntityWorld();
            if(!player.getEntityWorld().isRemote){
                spawnNpcDatabase(w.provider.getDimension(),w.getSpawnPoint(),w); // server spawn the npcs
            }else {
                spawnCustomClient();
            }
            spawned = true;
        }
        if(event.side.isClient() && event.player.world.provider.getDimension() != 0){
            CustomQuestManager.onPlayerTick(event);
        }
    }
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){
        if(!loaded && !spawned){
            WorldServer overAllWorldServer = DimensionManager.getWorld(0); // get the worldServer for overallWorld
            synchronized (overAllWorldServer.loadedEntityList){
                Iterator iter = overAllWorldServer.loadedEntityList.iterator();
                while(iter.hasNext()){
                    Entity entity = (Entity)iter.next();
                    if(entity instanceof EntityCustomNpc){
                        //System.out.println("");
                        AbstractCustomNpc npc = NpcDatabase.npcs.get(entity.getName());

                        if(npc != null){
                            System.out.println("CustomNPC " + npc.getName() + " is already spawned!");
                            npc.isSpawned = true;
                        }
                            //System.out.println(npc.getKey() + " is spwaned at " + "(" + pos.getX() + "," + pos.getY() + "," + pos.getZ()+ ")");
                    }
                }
            }
            loaded = true;
        }
    }
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event ){
        //System.out.println("A world is loaded, WorldEvent.Load triggerd");
        if(event.getWorld().provider.getDimension() == 0){
            System.out.println("Loading the overall world");
            //List<Entity> list = event.getWorld().getLoadedEntityList();
//            for(Entity e:list){
//                System.out.println(e.getName());
//            }
        }
//  NOTE: The quest start location is now managed under the EntityJoinWorldEvent
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
