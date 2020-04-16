package edu.ics.uci.minebike.minecraft.quests.customQuests;

import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import edu.ics.uci.minebike.minecraft.item.ItemGameFishingRod;

public class FishingQuest  extends AbstractCustomQuest {
    EntityPlayer player = null;
    public static ItemGameFishingRod rod;
    public static HudString powerString;
    public static HudString timerString;
    public static HudRectangle powerBar;
    public static HudRectangle powerLine;
    public static HudString distanceString;
    //private Vec3d ball_location = new Vec3d(10,10,10);

    public FishingQuest(){
        super();
        this.DIMID = WorldProviderFishing.DIM_ID;

    }
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {

    }

    @Override
    public boolean onPlayerJoin(EntityPlayer player) {
        give_rod(player);
        return true;
    }

    public void give_rod(EntityPlayer playerSP){
        //ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_rod"); //
        //Fishing rod name
        ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_fish_rod");
        this.rod = (ItemGameFishingRod)Item.REGISTRY.getObject(resourcelocation);
        ItemStack itemstack = new ItemStack(rod,1);
        playerSP.inventory.addItemStackToInventory(itemstack);
        playerSP.world.playSound((EntityPlayer)null, playerSP.posX, playerSP.posY, playerSP.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerSP.getRNG().nextFloat() - playerSP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        playerSP.inventoryContainer.detectAndSendChanges();

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
        System.out.println(" Start Fishing quest ");
        TextComponentString rod = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
        TextComponentString fish = new TextComponentString(String.format("/give %s Fish 2 ", this.player.getName()));
        this.player.sendMessage(rod);
        this.player.sendMessage(fish);
    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {

    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        rod.refresh_powerline();
        rod.reduce_distance();
        rod.refresh_timerString();
        if (rod.distance==0)
        {
            rod.unreg_hud();

        }

    }
}
