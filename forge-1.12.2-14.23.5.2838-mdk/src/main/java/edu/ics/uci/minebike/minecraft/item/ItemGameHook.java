package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;


public class ItemGameHook extends EntityFishHook {

    public ItemGameHook(World worldIn, EntityPlayer fishingPlayer) {
        super(worldIn, fishingPlayer);
    }
    public ItemGameHook(World worldIn, EntityPlayer p_i47290_2_, double x, double y, double z) {
        super(worldIn, p_i47290_2_, x, y, z);
    }


}