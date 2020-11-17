package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Elon extends AbstractCustomNpc {
    public static final String NAME = "Elon";
    public static final Vec3d LOCATION = new Vec3d(70,69,251); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/kingsteve.png";


    public Elon(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;

        // TODO: implement the quest system and new it here

        this.register();
    }

    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        System.out.println("Elon was interacted");
        AbstractCustomQuest mining = CustomQuestManager.customQuests.get(WorldProviderMiner.DIM_ID);
        System.out.println("Teleporting" + player.getName() + " DIM" + mining.DIMID);

        if(event.getWorld().isRemote){
            System.out.println("Teleporting" + player.getName() + " DIM" + mining.DIMID);
            ClientUtils.sendData(EnumPacketClient.PlayerJoin,"420");

            ClientUtils.teleport((EntityPlayerSP)player,mining.getStartLocation(),420);
        }
     //   else    // server teleport the user
     //       System.out.println("Cooking start location: "+ mining.questStartLocation+ "  " + mining.DIMID);
     //       ServerUtils.telport((EntityPlayerMP)player, mining.questStartLocation,mining.DIMID);
    }
}
