package edu.ics.uci.minebike.minecraft.quests;

//import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Scanner;

public class SoccerQuest extends AbstractCustomQuest {
    //EntitySoccerBall ball = null;
    EntityPlayer player = null;
    private Vec3d ball_location = new Vec3d(10,10,10);
    public static Vec3d questStartLocation = new Vec3d(11,10,11);
    public SoccerQuest(){

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
    public void start() {
//        if(this.ball == null){
//            System.out.println("Error, forget to initialize the Soccer Quest with setupQuestEnv(world) ");
//            return;
//        }
        //if(player.) started = true;

    }

    @Override
    public void end() {
        player = null;
        return;
    }
}
