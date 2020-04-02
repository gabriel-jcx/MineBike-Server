package edu.ics.uci.minebike.minecraft.quests.customQuests;

//import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

import java.util.Scanner;

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
        player = null;
        return;
    }
}
