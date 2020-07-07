package edu.ics.uci.minebike.minecraft.quests.customQuests;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.concurrent.TimeUnit;

public class Minequest extends AbstractCustomQuest {
    EntityPlayer player = null;


    public Minequest(){
        super();
        this.DIMID = WorldProviderMiner.DIM_ID;
        this.questStartLocation = new Vec3d(10,10,10);

    }
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {
        return;
    }

    @Override
    public boolean onPlayerJoin(EntityPlayer player) {
        return true;
    }

    @Override
    public void start(EntityPlayerMP playerMP) {
        //this.player = player;

        System.out.println(" Start Obama quest ");

        System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
        ServerUtils.telport((EntityPlayerMP) playerMP, questStartLocation, DIMID);

    }
    @Override
    public void start(){
    }

    @Override
    public void start(EntityPlayerSP player) {

    }

    @Override
    public void start(EntityJoinWorldEvent event) {

    }

    @Override
    public void end() {
        System.out.println(" Start Miner quest ");
    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {

    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    }



}
