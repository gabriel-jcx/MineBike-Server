package edu.ics.uci.minebike.minecraft.quests.customQuests;

import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class FishingQuest  extends AbstractCustomQuest {
    EntityPlayer player = null;
    //private Vec3d ball_location = new Vec3d(10,10,10);

    public FishingQuest(){
        super();
        this.DIMID = WorldProviderFishing.DIM_ID;
        this.questStartLocation = new Vec3d(11,10,11);
    }
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {

    }

    @Override
    public void start(EntityPlayerMP playerMP) {
        //this.player = player;

        System.out.println(" Start Fishing quest ");
//        TextComponentString give = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
//        this.player.sendMessage(give);
        System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
        ServerUtils.telport((EntityPlayerMP) playerMP, questStartLocation, DIMID);

    }

    @Override
    public void start(EntityJoinWorldEvent event) {

    }

    @Override
    public void end() {
        System.out.println(" Start Fishing quest ");
        TextComponentString rod = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
        TextComponentString fish = new TextComponentString(String.format("/give %s Fish 2 ", this.player.getName()));
        this.player.sendMessage(rod);
        this.player.sendMessage(fish);
    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {

    }
}
