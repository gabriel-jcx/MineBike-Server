package edu.ics.uci.minebike.minecraft.quests;

//import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Scanner;

import static net.minecraftforge.items.ItemHandlerHelper.giveItemToPlayer;

public class SoccerQuest extends AbstractCustomQuest {
    //EntitySoccerBall ball = null;
    EntityPlayer player = null;
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
    public void end() {
        int numDiamonds = 10;   //can multiply by a scalar depending on difficulty
        giveItemToPlayer(this.player, new ItemStack(Items.DIAMOND, numDiamonds));
        player = null;
        return;
    }
}
