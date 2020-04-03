package edu.ics.uci.minebike.minecraft.quests.customQuests;

//import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
<<<<<<< HEAD:forge-1.12.2-14.23.5.2838-mdk/src/main/java/edu/ics/uci/minebike/minecraft/quests/SoccerQuest.java
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

=======
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
>>>>>>> c6b8b59feb8ee08aec3536323df15f09101aa052:forge-1.12.2-14.23.5.2838-mdk/src/main/java/edu/ics/uci/minebike/minecraft/quests/customQuests/SoccerQuest.java
import net.minecraftforge.fml.common.registry.GameRegistry;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

import java.util.Scanner;

import static net.minecraftforge.items.ItemHandlerHelper.giveItemToPlayer;

public class SoccerQuest extends AbstractCustomQuest {
    //EntitySoccerBall ball = null;
    EntityPlayer player = null;
    EntitySoccerBall ball;
    private final Vec3d ball_location = new Vec3d(10,10,10);
    public SoccerQuest(){
        super();
        this.DIMID = 222;
        this.questStartLocation = new Vec3d(11,10,11);
    }

    @Override
    public void setupQuestEnv(World world, EntityPlayer player) {
        //this.ball = new EntitySoccerBall(world);
        this.player = player;
        if(!world.isRemote ){ // only set the location of the ball on the server
            //ball.setPosition(ball_location.x,ball_location.y,ball_location.z);
            //world.spawnEntity(ball);

        }
    }

    @Override
    public void start(EntityPlayerMP player) {
//        if(this.ball == null){
//            System.out.println("Error, forget to initialize the Soccer Quest with setupQuestEnv(world) ");
//            return;
//        }
        //if(player.) started = true;

    }

    @Override
    public void start(EntityJoinWorldEvent event) {
        ICustomNpc npc = NpcAPI.Instance().spawnNPC(event.getWorld(),10, 5,10 );
        npc.setName("a");
        if(npc instanceof  EntityCustomNpc){
            System.out.println("The created CustomNPC is actually a EntityCustomNPc");
        }

        // Spwan a ball!
        ball = new EntitySoccerBall(event.getWorld());
        WorldServer ws = DimensionManager.getWorld(222);
        ball.setPosition(ball_location.x,ball_location.y,ball_location.z);
        ws.spawnEntity(ball);


        // spawn associated NPC and ball if not spawned
    }

    @Override
    public void end() {
        int numDiamonds = 10;   //can multiply by a scalar depending on difficulty
        giveItemToPlayer(this.player, new ItemStack(Items.DIAMOND, numDiamonds));

        player = null;
        return;
    }
}
