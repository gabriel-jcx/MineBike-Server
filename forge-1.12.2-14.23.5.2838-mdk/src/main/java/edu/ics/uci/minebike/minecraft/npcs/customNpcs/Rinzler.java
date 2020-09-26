package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Rinzler extends AbstractCustomNpc {
    public static final String NAME = "Rinzler";
    public static final Vec3d LOCATION = new Vec3d(0,5,10); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/kingsteve.png";

    public Rinzler(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;

        // TODO: implement the quest system and new it here

        this.register();

    }
    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isRemote){
            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
            s.getCommandManager().executeCommand(s,"/tpx " +
                    event.getEntityPlayer().getName() + " 10 10 10 222");
        }else{
            if(event.getWorld().isRemote){} // client send packet
                //ClientUtils.sendData(EnumPacketClient.PlayerJoin,"250");
        }
         System.out.println("Rinzler was interacted");
    }

}
