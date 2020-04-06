package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.entity.EntityCustomNpc;

public class Ada extends AbstractCustomNpc {
    public static final String NAME = "Ada";
    public static final Vec3d LOCATION = new Vec3d(6,0,-3); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanfemale/stephanie.png";
    public Ada(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;
        this.register();
    }
    @SideOnly(Side.CLIENT)
    public static void give_rod(EntityPlayer playerSP){
        //ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_rod"); //
        //Fishing rod name
        ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_fish_rod");
        Item rod = Item.REGISTRY.getObject(resourcelocation);
        ItemStack itemstack = new ItemStack(rod,1);
        playerSP.inventory.addItemStackToInventory(itemstack);
        playerSP.world.playSound((EntityPlayer)null, playerSP.posX, playerSP.posY, playerSP.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerSP.getRNG().nextFloat() - playerSP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        playerSP.inventoryContainer.detectAndSendChanges();

    }
    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        System.out.println("Ada was interacted");
        for(EntityCustomNpc npc: NpcDatabase.npc_entities){
            //System.out.println(npc.getName());
            if(npc.getName().equals(this.name)){

                System.out.println(npc.getName() + " is deleted");
            }
        }
        AbstractCustomQuest fishing = CustomQuestManager.customQuests.get(WorldProviderFishing.DIM_ID);
        boolean isJoinSuccess = fishing.onPlayerJoin(player);
        if(isJoinSuccess){
            if(event.getWorld().isRemote){  // Client side send message
                FishingQuest fishingQuest = new FishingQuest();
                System.out.println(" Start Fishing quest ");
                //TODO: The Dialog
                //TextComponentString give = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", player.getName()));
                give_rod( player);
                ClientUtils.sendData(EnumPacketClient.PlayerJoin,"223");

                System.out.println("is Client Side!!!!");
            }
            else {
                give_rod( player);
                ServerUtils.telport((EntityPlayerMP)player, fishing.questStartLocation,fishing.DIMID);
            }
//            if(!event.getWorld().isRemote){
//
//                //telport((EntityPlayerMP) player, FishingQuest.questStartLocation, WorldProviderFishing.DIM_ID);
//            }
        }else{

            System.out.println(player.getName() + " join ");
            // Show soccer join error message here!
        }
    }
}
