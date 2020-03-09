package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

public class Carissa extends AbstractCustomNpc {
    public static final String NAME = "Carissa";
    public static final Vec3d LOCATION = new Vec3d(116,70,10); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanfemale/robesblackstephanie.png";
    public Carissa(){
        this.register();
    }

    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        System.out.println(NAME + " is interacted");
    }
}
