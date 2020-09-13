package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.SoccerQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noppes.npcs.entity.EntityCustomNpc;


/*
    Jaya is the costum NPC for the soccer quest
    Mainly performs sending the custom packet : PlayerJoin, to the server
*/
public class Jaya extends AbstractCustomNpc {
    public static final String NAME = "Jaya";
    //    public static final Vec3d LOCATION = new Vec3d(10,70,13);
    public static final Vec3d LOCATION = new Vec3d(-55, 64, 319);
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/tradersteve.png";
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
        AbstractCustomQuest soccer = CustomQuestManager.customQuests.get(WorldProviderSoccerQuest.DIM_ID);

        if(event.getWorld().isRemote) // client send packet
            ClientUtils.sendData(EnumPacketClient.PlayerJoin,"222");
//        else    // server teleport the user
//            ServerUtils.telport((EntityPlayerMP)player, soccer.questStartLocation,soccer.DIMID);


    }

}
