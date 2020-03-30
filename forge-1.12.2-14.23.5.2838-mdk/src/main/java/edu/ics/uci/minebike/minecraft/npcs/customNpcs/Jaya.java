package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.SoccerQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.entity.EntityCustomNpc;

public class Jaya extends AbstractCustomNpc{
    public static final String NAME = "Jaya";
    public static final Vec3d LOCATION = new Vec3d(10,70,13); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/tradesteve.png";
    public static String UUID;

    public Jaya(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;

        // TODO: implement the quest system and new it here

        this.register();

    }
    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        System.out.println("Jaya was interacted");

        for(EntityCustomNpc npc: NpcDatabase.npc_entities){
            //System.out.println(npc.getName());
            if(npc.getName().equals(this.name)){

                npc.delete();
                System.out.println(npc.getName() + " is deleted");
            }
        }
        AbstractCustomQuest soccer = CustomQuestManager.quest_list.get(0);
        boolean isJoinSuccess = soccer.onPlayerJoin(player);
        if(isJoinSuccess){
//            if(event.getWorld().isRemote){  // Client side send message
//                telport((EntityPlayerSP) player, SoccerQuest.questStartLocation, WorldProviderSoccerQuest.DIM_ID);
//                System.out.println("is Client Side!!!!");
//                //player.sendMessage(new TextComponentString("/tpx 222 10 10 10"));
//            }
            if(!event.getWorld().isRemote) {
                System.out.println("/tpx " + event.getEntityPlayer().getName() + " 10 10 10");

                ((EntityCustomNpc) event.getTarget()).sendMessage(new TextComponentString("/tpx " + event.getEntityPlayer().getName() + " 10 10 10"));
            }
              if(!event.getWorld().isRemote){
                  NoppesUtilPlayer.teleportPlayer((EntityPlayerMP)player,10,10,10,222);
              }
        }else{
            System.out.println(player.getName() + " join ");
            // Show soccer join error message here!
        }
    }

}
