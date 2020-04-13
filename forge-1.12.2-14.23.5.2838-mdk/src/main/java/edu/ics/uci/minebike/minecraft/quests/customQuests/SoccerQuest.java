package edu.ics.uci.minebike.minecraft.quests.customQuests;

import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.HudManager;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Jaya;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.data.DataAI;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static net.minecraftforge.items.ItemHandlerHelper.giveItemToPlayer;



// NOTE: many of the fields can be more optimized i think, getting lazy now LOL
public class SoccerQuest extends AbstractCustomQuest {

    private final long GAME_WAITING_TIME = 10000;//30000; // millisecond
    private final long GAME_SESSION_TIME = 10000;//300000; // millisecond => equivalent to 5 mins
    private final int GOAL_TICK_TIME = 5; // If ball stays in the goal for 5 ticks
    private final Vec3d ball_location = new Vec3d(-165, 4,1145);


    // A place holder for now for the goal locations
    private final GoalRectangle blueGoal = new GoalRectangle(0,0,10,10);
    private final GoalRectangle redGoal = new GoalRectangle(20,20,30, 30);

    // Soccer ball fields
    private EntitySoccerBall ball;

    // WorldServer
    public WorldServer soccerWS = null;

    // npc AI fields
    // public static List<int[]> npcPathList = new ArrayList<int[]>();

    // Player fields
    public ArrayList<EntityPlayerMP> playersInGame  = new ArrayList<>();

    // Server waiting Tracker
    private long server_waitingStartTime = 0;
    private long server_waitingEndTime = 0;
    private long server_waitingTime = GAME_WAITING_TIME;
    private int server_waitingTime_seconds = (int)(GAME_WAITING_TIME/1000);


    // Client waiting Tracker
    private long client_waitingTime  = 0;
    private long client_waitingStartTime = 0;
    private long client_waitingEndTime = 0;
    private int client_waitingTime_seconds = 0;

    // Server start Tracker
    private long server_endTime = 0;
    private long server_startTime = 0;
    private int ball_in_goal_tick = 0;


    // Client start Tracker
    private long client_startTime = 0;
    private long client_endTime = 0;




    // Soccer field goal locations
//    BlockPos leftGoal

//    @SideOnly(Side.CLIENT)

//    private HudRectangle clockRect;
    private HudString clockStr;
    private HudRectangle scoreLeftRect;
    private int scoreLeft = 0;
    private HudString scoreLeftStr;
    private HudRectangle scoreRightRect;
    private int scoreRight = 0;
    private HudString scoreRightStr;
    private long waitingEndTime;




    public boolean isWaiting = false;


    // Temp flag
    public boolean testFlag = false;

    public SoccerQuest(){
        super();
        this.DIMID = 222;
        this.isStarted = false;
        this.questStartLocation = new Vec3d (-160, 4,1142);
        //this.questStartLocation = new Vec3d(11,10,11);
//        InventoryPlayer p = player.inventory;
//        p.

    }

    // This onPlayerJoin is only called on the server side
    @SideOnly(Side.SERVER)
    @Override
    public boolean onPlayerJoin(EntityPlayer player){
//        if(!isStarted && isWaiting)
        System.out.println("On PlayerJoin triggerd on server side");

        if(isStarted){

            System.out.println("There's an ongoing soccer session, please wait!");
            ServerUtils.sendQuestData(EnumPacketServer.QuestJoinFailed,(EntityPlayerMP)player, Long.toString(this.server_waitingTime));

            return false;
        }

        // teleporting here seems to be a problem!
            if(!isWaiting) {
                server_waitingStartTime = System.currentTimeMillis();
                server_waitingEndTime = server_waitingStartTime + server_waitingTime;
                WorldServer ws = DimensionManager.getWorld(this.DIMID);
//                synchronized(ws.getLoadedEntityList()){
//                    Iterator iter = ws.getLoadedEntityList().iterator();
//                    while(iter.hasNext()){
//                        Entity entity = (Entity)iter.next();
//                        if(!(entity instanceof EntityPlayer))
//                            entity.isDead = true;
//                    }
//                }
                isWaiting = true;
                //waitingEndTime = waitingStartTime + waitingTime;
            }
            Potion slow_potion = Potion.getPotionById(2);
            Potion jump_anti_boost = Potion.getPotionById(8);
            System.out.println(slow_potion.getName()+ " " + jump_anti_boost.getName());
            int secs = QuestUtils.getRemainingSeconds(server_waitingEndTime -System.currentTimeMillis());
            System.out.println(secs);
            // I think the duration is in Ticks
            //player.addPotionEffect(new PotionEffect(slow_potion,secs*20,1000000000));

            //player.addPotionEffect(new PotionEffect(jump_anti_boost, secs*20, 128));
            ServerUtils.sendQuestData(EnumPacketServer.SoccerQueueingTime,(EntityPlayerMP)player, Long.toString(this.server_waitingTime));
            playersInGame.add((EntityPlayerMP)player);
            return true;

    }

    @Override
    public void setupQuestEnv(World world, EntityPlayer player) {
        //this.ball = new EntitySoccerBall(world);
        this.player = player;
        ;
        if(!world.isRemote ){ // only set the location of the ball on the server
            //ball.setPosition(ball_location.x,ball_location.y,ball_location.z);
            //world.spawnEntity(ball);

        }
    }

    // This is the server side of starting, send a trigger packet to each Player for starting the quest!
    @Override
    public void start(EntityPlayerMP player) {
        System.out.println("start is triggered for " + player.getName());
        // The DIMID is used for mapping QuestStart to this quest
        ServerUtils.sendQuestData(EnumPacketServer.QuestStart, player, Long.toString(this.DIMID));
        // NOTE: this start is temporarily deprecated!
    }

    @Override
    public void start(EntityPlayerSP player){

    }
    @Override
    public void start(EntityJoinWorldEvent event) {
//        if(isStarted){
//            System.err.println("Error: The Soccer Quest is already started!");
//            return;
//        }
//        soccerWS = DimensionManager.getWorld(222);
//
//        ICustomNpc npc = NpcAPI.Instance().spawnNPC(event.getWorld(),10, 5,10 );
//
//        if(npc.getAi() instanceof  DataAI){
//            System.out.println("INPCai is instance of DataAI");
//        }
//        int[] pos = new int[]{20,5,20};
//        DataAI npcai = (DataAI)npc.getAi();
//        npcPathList.add(0,pos);
//
//        npcPathList.add(1,pos);
//        npcai.setMovingPath(npcPathList);
//        npc.getAi().setMovingPathType(2,false);
//        //npc.setMoveForward();
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
//        ball = new EntitySoccerBall(event.getWorld());
//        ball.setPosition(ball_location.x,ball_location.y,ball_location.z);
//        soccerWS.spawnEntity(ball);
//
//        this.isStarted = true;
        // spawn associated NPC and ball if not spawned
    }


    @Override
    public void start(){ // This is the start for client

        client_startTime = System.currentTimeMillis();
        client_endTime = client_startTime + GAME_SESSION_TIME;
        isWaiting = false;
        isStarted = true;


        // Hud Elements
        clockStr.y -= 20;
        clockStr.scale = 1.0f; // make it smaller during the game
//        scoreLeftRect = new HudRectangle();
        scoreLeftStr = new HudString(-40,35, Integer.toString(scoreLeft), 1.5f, 0x00ff0000, true, false);
////        scoreRightRect = new HudString();
        scoreRightStr = new HudString(40,35, Integer.toString(scoreRight),1.5f, 0x000000ff, true, false);
        System.out.println("Left = " + scoreLeft + " , Right = " + scoreRight );

        // Here need to
    }
    @Override
    public void end() {

        if(soccerWS != null && !soccerWS.isRemote){
            int numDiamonds = 10;   //can multiply by a scalar depending on difficulty
            for(EntityPlayer player: this.playersInGame){
                giveItemToPlayer(player, new ItemStack(Items.DIAMOND, numDiamonds));
//                ServerUtils.telport((EntityPlayerMP)player, Jaya.LOCATION,0);
            }
            //ball.isDead = true;
            soccerWS.removeEntity(ball);
            ball = null;

        }else{
            clockStr.unregister();
            if(isStarted){
                scoreLeftStr.unregister();
                scoreRightStr.unregister();
            }
        }
        isStarted = false; // set both client and server to not
        return;
    }


    // NOTE: Minecraft runs 20 ticks per second
    //       Every tick is 0.05 seconds and 50 milliseconds
    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if(!event.world.isRemote){ // Server side
            if(isWaiting){
                this.serverWaitingTick(event);
            }else if(isStarted){
                // Figure out what server need to do for each tick?
                this.serverStartTick(event);
            }
        }else{ // Client Side
//            event.world.getChunkFromBlockCoords().
//
        }
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(isWaiting){
            this.clientWaitingTick(event);
        }else if(isStarted){
            this.clientStartTick(event);
        }
    }
    private void serverStartTick(TickEvent.WorldTickEvent event){
        long curr = System.currentTimeMillis();
        if(ball != null){
            if(isBallInsideGoal(ball.getPosition(),blueGoal) || isBallInsideGoal(ball.getPosition(), redGoal)){
                this.ball_in_goal_tick++;
            }
            if(ball_in_goal_tick > GOAL_TICK_TIME){
                if(isBallInsideGoal(ball.getPosition(),blueGoal)){
                    scoreLeft++;
                }else if(isBallInsideGoal(ball.getPosition(),redGoal)){
                    scoreRight++;
                }
                ball.setPosition(ball_location.x, ball_location.y, ball_location.z); // reset the ball pos
            }
        }
        if(curr >= server_endTime){
            // end the game session for everyone!
            System.out.println("GAME ENDSSSSS");
            isStarted = false;
            this.end();
        }
        // server need to check position of the ball in each tick and determines if ball needs
        // to be respawned back at the initial location

        // Everytime a goal happens, need to transmit the packet to each client for updating the score
    }
    private boolean isBallInsideGoal(BlockPos ball, GoalRectangle goal){
        if(ball.getX() > goal.x1 && ball.getX() < goal.x2
        && ball.getY() > goal.y1 && ball.getY() < goal.y2)
            return true;
        return false;
    }

    private boolean entityInsideLeftGoal (EntityLiving entity){
        return false;
    }
    private boolean entityInsideRightGoal (EntityLiving entity){
        return false;
    }
    private void clientStartTick(TickEvent.PlayerTickEvent event){
        long curr = System.currentTimeMillis();

        // The logic works based on the fact that the GAME last more than 3 seconds!!!
        if(curr - client_startTime < 5000) // 3000ms for displaying GAMESTART!
            clockStr.text = "GAME START! KICK THE BALL TOWARDS THE GOAL!~";
        else if(curr >= client_endTime){
            System.out.println("Game END on client!");
            this.end();
        }
        else{
            long remaining_millisecs = client_endTime - curr;
            clockStr.text = QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(remaining_millisecs));
        }
    }


    private void serverWaitingTick(TickEvent.WorldTickEvent event){
        long curr = System.currentTimeMillis();
        int elapsed_seconds = QuestUtils.getRemainingSeconds(curr,server_waitingStartTime);
//        System.out.println(elapsed_seconds + " " + server_waitingTime_seconds);

        if(elapsed_seconds < server_waitingTime_seconds){
            // Decrement milliseconds count for Client

//            server_waitingTime = server_waitingEndTime - curr;  It seems unnecessary to do this computation here

        }else{
            // NOTE: this section re-initialize the waiting state and trigger start for User

            server_waitingTime = GAME_WAITING_TIME; // resetting the timer param


            soccerWS = DimensionManager.getWorld(this.DIMID);

            soccerWS.setWorldTime(500); // set time to day

            // Spawning ball
            ball = new EntitySoccerBall(event.world);
            ball.setPosition(ball_location.x,ball_location.y,ball_location.z);
            soccerWS.spawnEntity(ball);


            for(EntityPlayerMP player: this.playersInGame){
                this.start(player); // event game start triggered
            }
            server_startTime = System.currentTimeMillis();
            server_endTime = server_startTime + GAME_SESSION_TIME;
            // Set game state
            isStarted = true;
            isWaiting = false;
        }
    }
    public void clientStartWaiting(String waitingTime){
        client_waitingTime = Long.parseLong(waitingTime);
        client_waitingStartTime = System.currentTimeMillis();

        client_waitingTime_seconds = QuestUtils.getRemainingSeconds(client_waitingTime);
        client_waitingEndTime = client_waitingStartTime + client_waitingTime;
        System.out.println("Client waiting for " + client_waitingTime_seconds + " seconds");
//        QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(client_waitingTime));

        //clockRect = new HudRectangle(-30, 30, 60, 30, 0x00000000, true, false);
        clockStr = new HudString(0,35, QuestUtils.formatSeconds(client_waitingTime_seconds),2.0f,true, false);

        isWaiting = true;
    }
    public void clientWaitingTick(TickEvent.PlayerTickEvent event){

//        int elpased_seconds = QuestUtils.getRemainingSeconds(System.currentTimeMillis(),client_waitingStartTime);
        client_waitingTime = client_waitingEndTime - System.currentTimeMillis();

        int remaining_seconds = QuestUtils.getRemainingSeconds(client_waitingTime);
        if(remaining_seconds >= 0 ){
            clockStr.text = QuestUtils.formatSeconds(remaining_seconds);
        }

//        System.out.println("Client have " + clockStr.text + "left");
    }

}
