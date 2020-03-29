package edu.ics.uci.minebike.minecraft.npcs.customNpcs;


import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.FishingQuest;
import edu.ics.uci.minebike.minecraft.quests.SoccerQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noppes.npcs.entity.EntityCustomNpc;
public class Ada extends AbstractCustomNpc {
    public static final String NAME = "Jaya";
    public static final Vec3d LOCATION = new Vec3d(111,60,5); // TODO: figure out the location
    public static final String TEXTURE_NAME = "customnpcs:textures/entity/humanfemale/stephanie.png";
    public Ada(){
        name = NAME;
        location = LOCATION;
        texture = TEXTURE_NAME;
        this.register();
    }
    @Override
    public void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event) {
        System.out.println("Ada was interacted");
        for(EntityCustomNpc npc: NpcDatabase.npc_entities){
            //System.out.println(npc.getName());
            if(npc.getName().equals(this.name)){
                npc.delete();
                System.out.println(npc.getName() + " is deleted");
            }
        }
        AbstractCustomQuest soccer = CustomQuestManager.quest_list.get(1);
        boolean isJoinSuccess = soccer.onPlayerJoin(player);
        if(isJoinSuccess){
            if(event.getWorld().isRemote){  // Client side send message
                telport((EntityPlayerSP) player, FishingQuest.questStartLocation, WorldProviderFishing.DIM_ID);
                System.out.println("is Client Side!!!!");
                //player.sendMessage(new TextComponentString("/tpx 222 10 10 10"));
            }
        }else{
            System.out.println(player.getName() + " join ");
            // Show soccer join error message here!
        }
    }
}
