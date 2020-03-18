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
import noppes.npcs.entity.EntityCustomNpc;

public class NpcEventHandler {
    public static void customNpcInteract(EntityPlayer player, PlayerInteractEvent.EntityInteract event){
        //player.sendMessage(new TextComponentString("/teleport " + player.getName()+" ~2 ~3 ~1"));
        AbstractCustomNpc npc = NpcDatabase.npcs.get(event.getTarget().getName());
        System.out.println();
        if(npc == null){
            System.out.println("Player interacted with NPC:" + event.getTarget().getName() +" but not in the NPC database");

            return;
        }else{
            System.out.println(player.getName() + " interacted with " + npc.getName());
            npc.onInteraction(player,event);
        }
//        event.getTarget().getName()
//        System.out.println("Target UUID:" + event.getTarget().getUniqueID() );
//        for(AbstractCustomNpc npc: NpcDatabase.customNpcs){
//            System.out.println("NPC UUID:" + npc.getUUID() );
//            if(npc.getUUID().equals(event.getTarget().getUniqueID().toString())) {
//                System.out.println("NPC " + npc.getName() + " was interacted");
//                npc.onInteraction(player, event);
//            }
//        }
    }

}
