package edu.ics.uci.minebike.minecraft.quests.customQuests;

import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.data.DataAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static net.minecraftforge.items.ItemHandlerHelper.giveItemToPlayer;

public class SoccerQuest extends AbstractCustomQuest {
    //EntitySoccerBall ball = null;
    private EntityPlayer player = null;
    private EntitySoccerBall ball;
    public WorldServer soccerWS = null;
    public static List<int[]> npcPathList = new ArrayList<int[]>();
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
        // NOTE: this start is temporarily deprecated!
    }

    @Override
    public void start(EntityJoinWorldEvent event) {
        if(isStarted){
            System.err.println("Error: The Soccer Quest is already started!");
            return;
        }
        soccerWS = DimensionManager.getWorld(222);

        ICustomNpc npc = NpcAPI.Instance().spawnNPC(event.getWorld(),10, 5,10 );

        if(npc.getAi() instanceof  DataAI){
            System.out.println("INPCai is instance of DataAI");
        }
        int[] pos = new int[]{20,5,20};
        DataAI npcai = (DataAI)npc.getAi();
        npcPathList.add(0,pos);

        npcPathList.add(1,pos);
        npcai.setMovingPath(npcPathList);
        npc.getAi().setMovingPathType(2,false);
        //npc.setMoveForward();
//        DataAI npcai = (DataAI)npc.getAi();
//        npcai.setStartPos(new BlockPos(10,5,10));
//        int[] newPosition = new int[] {20,5,20};
//        npcai.getMovingPath().add(newPosition);
//        npcai.setMovingType(0);
//        npcai.canSprint = true;
//        npcai.movingPause = false;
        //npcai.setMovingPath(new List<int>{20,20,20});
//        BlockPos pos = new BlockPos(20, 20, 20);
//
//        npc.getAi().setMovingType(2); // 2 for
//        npc.getAi().getMovingPathType()
//        npc.setMoveForward(200);
        //npc.getAi().setMovingPathType();
//        EntityCustomNpc npc = new EntityCustomNpc(event.getWorld());
//        npc.wrappedNPC.setName("a");
//        npc.ais.setStartPos(npc.getPosition());
//        boolean spawned = soccerWS.spawnEntity(npc);
//        soccerWS.updateEntities();
//        if(spawned){
//            System.out.println("Spawn successful, but can you see it?");
//        }
//        if(npc instanceof  EntityCustomNpc){
//            System.out.println("The created CustomNPC is actually a EntityCustomNPc");
//        }

        // Spwan a ball!
        ball = new EntitySoccerBall(event.getWorld());
        ball.setPosition(ball_location.x,ball_location.y,ball_location.z);
        soccerWS.spawnEntity(ball);

        this.isStarted = true;
        // spawn associated NPC and ball if not spawned
    }

    @Override
    public void end() {
        int numDiamonds = 10;   //can multiply by a scalar depending on difficulty
        giveItemToPlayer(this.player, new ItemStack(Items.DIAMOND, numDiamonds));

        player = null;
        soccerWS.removeEntity(ball);
        isStarted = false;
        return;
    }
}
