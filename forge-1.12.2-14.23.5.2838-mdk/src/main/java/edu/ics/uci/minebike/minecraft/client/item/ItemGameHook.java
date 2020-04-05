package edu.ics.uci.minebike.minecraft.client.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;


public class ItemGameHook extends EntityFishHook {

    public ItemGameHook(World worldIn, EntityPlayer fishingPlayer) {
        super(worldIn, fishingPlayer);
    }

}