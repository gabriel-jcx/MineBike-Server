package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Sam extends AbstractCustomNpc {
    public static final String NAME = "Sam";
    public static final Vec3d LOCATION = new Vec3d(0,10,0); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/kingsteve.png";

    public Sam(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;

        // TODO: implement the quest system and new it here

        this.register();

    }
    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        /*if(!event.getWorld().isRemote){
            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
            s.getCommandManager().executeCommand(s,"/tpx " +
                    event.getEntityPlayer().getName() + " 10 10 10 222");
        }*/
         System.out.println("Sam was interacted");
    }

}
