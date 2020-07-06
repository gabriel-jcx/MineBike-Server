package edu.ics.uci.minebike.minecraft.npcs.customNpcs;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.OverCookedQuest;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.entity.EntityCustomNpc;

public class ChefGusteau extends AbstractCustomNpc{
    public static final String NAME = "ChefGusteau";
    public static final Vec3d LOCATION = new Vec3d(7,78,-3);
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanfemale/Chef_Gusteau.png";
    public ChefGusteau()
    {
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;
        this.register();
    }

    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event)
    {
        System.out.println("Chef G was interacted");
        for(EntityCustomNpc npc: NpcDatabase.npc_entities)
        {
            if(npc.getName().equals(this.name)){

                npc.delete();
                System.out.println(npc.getName() + " is deleted");
            }
        }
        AbstractCustomQuest cooked = CustomQuestManager.customQuests.get(WorldProviderOverCooked.DIM_ID);

        if(event.getWorld().isRemote)
        {
            ClientUtils.sendData(EnumPacketClient.PlayerJoin, "723");
//            System.out.println("is Client Side!!!!");
        }
//        else
//        {
//            System.out.println("Cooking start location: "+ cooked.questStartLocation);
//            ServerUtils.telport((EntityPlayerMP)player, cooked.questStartLocation,cooked.DIMID);
//        }
    }

}
