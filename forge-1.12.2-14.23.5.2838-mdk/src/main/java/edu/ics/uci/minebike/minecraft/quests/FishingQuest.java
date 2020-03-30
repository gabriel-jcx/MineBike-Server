package edu.ics.uci.minebike.minecraft.quests;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import static edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc.telport;

public class FishingQuest  extends AbstractCustomQuest {
    EntityPlayer player = null;
    //private Vec3d ball_location = new Vec3d(10,10,10);
    public static Vec3d questStartLocation = new Vec3d(11,10,11);
    public FishingQuest(){

    }
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {
        this.player = player;
    }

    @Override
    protected void start() {
        System.out.println(" Start Fishing quest ");
        TextComponentString give = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
        this.player.sendMessage(give);
        telport((EntityPlayerSP) player, FishingQuest.questStartLocation, WorldProviderFishing.DIM_ID);
    }

    @Override
    protected void end() {
        System.out.println(" Start Fishing quest ");
        TextComponentString rod = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
        TextComponentString fish = new TextComponentString(String.format("/give %s Fish 2 ", this.player.getName()));
        this.player.sendMessage(rod);
        this.player.sendMessage(fish);
    }
}
