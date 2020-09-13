package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.OrderHolder;
import edu.ics.uci.minebike.minecraft.quests.customQuests.OverCookedQuest;
import edu.ics.uci.minebike.minecraft.quests.customQuests.Recipe;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderOverCooked;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.entity.EntityCustomNpc;
import org.jline.terminal.impl.jna.osx.CLibrary;

import java.util.ArrayList;

public class Waiter extends AbstractCustomNpc{
    public static final String NAME = "Waiter";
    public static final Vec3d LOCATION = new Vec3d(543,7,-3);
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanmale/tuxedosteve.png";
    public Waiter(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;
        this.register();
    }

    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event){
        if(event.getWorld().isRemote) {
//            OverCookedQuest cook = (OverCookedQuest) CustomQuestManager.customQuests.get(723);
//            ArrayList<Recipe> orders = cook.orders.getFoods();
//            if(orders.size() > 0) {
//                Recipe curOrder = orders.get(0);
//                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
//                    if (player.inventory.getStackInSlot(i) != null) {
//                        if (player.inventory.getCurrentItem().getDisplayName() == curOrder.getName()) {
//                            ClientUtils.sendData(EnumPacketClient.OrderSubmit, curOrder.getName());
//                            return;
//                        }
//                    }
//                }
//                player.sendStatusMessage(new TextComponentTranslation("No Orders can be made", new Object[0]).setStyle((new Style()).setColor(TextFormatting.AQUA)),false);
//
//            }
            System.out.println("Asking server if order is complete");
            ClientUtils.sendData(EnumPacketClient.OrderSubmit);
        }
    }
}
