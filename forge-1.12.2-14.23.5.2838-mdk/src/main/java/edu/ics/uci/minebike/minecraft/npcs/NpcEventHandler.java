package edu.ics.uci.minebike.minecraft.npcs;

import ca.weblite.objc.Client;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NpcEventHandler {
    public static void customNpcInteract(EntityPlayer player, PlayerInteractEvent.EntityInteract event){

        //player.sendMessage(new TextComponentString("/teleport " + player.getName()+" ~2 ~3 ~1"));
        if(event.getWorld().isRemote)
            playerSendChat(player);
        System.out.println("Target UUID:" + event.getTarget().getUniqueID() );
        for(AbstractCustomNpc npc: NpcDatabase.customNpcs){
            System.out.println("NPC UUID:" + npc.getUUID() );
            if(npc.getUUID().equals(event.getTarget().getUniqueID().toString())) {
                System.out.println("NPC " + npc.getName() + " was interacted");
                npc.onInteraction(player, event);
            }
        }
    }
    @SideOnly(Side.CLIENT)
    public static void playerSendChat(EntityPlayer player){
        ((EntityPlayerSP)player).sendChatMessage("/teleport " + player.getName()+" ~5 ~ ~");
    }
}
