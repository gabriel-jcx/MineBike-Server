package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class Sam extends AbstractCustomNpc {
    public static final String NAME = "Sam";
    public static final Vec3d LOCATION = new Vec3d(113,70,10); // TODO: figure out the location
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
        System.out.println("Sam was interacted");
    }
}
