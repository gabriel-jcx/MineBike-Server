package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderTRONQuest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.entity.EntityCustomNpc;
import net.minecraftforge.fml.common.FMLCommonHandler;


public class Renzler extends AbstractCustomNpc {
    public static final String NAME = "Renzler";
    public static final Vec3d LOCATION = new Vec3d(0, 20, 0); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/kingsteve.png";
    public Renzler(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;
        this.register();

    }
    //@SideOnly(Side.CLIENT)

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if(!e.player.world.isRemote) {

        }
    }

    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        if(!event.getWorld().isRemote) {
          MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
          s.getCommandManager().executeCommand(s, "/tpx 250 0 10 0" );
        }

        System.out.println("Rinzler was interacted");
        AbstractCustomQuest TRON = CustomQuestManager.customQuests.get(WorldProviderTRONQuest.DIM_ID);
       // ServerUtils.sendQuestData(EnumPacketServer.QuestJoinFailed,(EntityPlayerMP)player, Long.toString(this.server_waitingTime));

        //boolean isJoinSuccess = fishing.onPlayerJoin(player);
        if(event.getWorld().isRemote){  // Client side send message
            ClientUtils.sendData(EnumPacketClient.PlayerJoin,"250");
            System.out.println("is Client Side!!!!");
        }
        /*else {
            System.out.println("fish start location: "+fishing.questStartLocation+"   "+fishing.DIMID);
            ServerUtils.telport((EntityPlayerMP)player, fishing.questStartLocation,fishing.DIMID);
//            give_rod( player);
            //ServerUtils.telport((EntityPlayerMP)player, fishing.questStartLocation,fishing.DIMID);
        }
//            if(!event.getWorld().isRemote){
//
//                //telport((EntityPlayerMP) player, FishingQuest.questStartLocation, WorldProviderFishing.DIM_ID);
//            }*/

    }
}
