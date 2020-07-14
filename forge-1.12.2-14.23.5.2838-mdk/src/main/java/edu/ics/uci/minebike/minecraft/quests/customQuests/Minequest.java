package edu.ics.uci.minebike.minecraft.quests.customQuests;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.time.Clock;
import java.util.ArrayList;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.concurrent.TimeUnit;

public class Minequest extends AbstractCustomQuest {
    EntityPlayer player = null;
    public int DIMID;
    private boolean isStarted;
    private boolean isWaiting;
    public Vec3d questStartLocation;
    private int score = 0;
    private int ticks = 0;
    private boolean countTicks;
    private long gameTime;
    private long waitTime;
    public static final int MINERUNDIMENSIONID = 220;
		private boolean pleaseStop;
  //OLD VARIABLES START HERE
  //  private ChunkCoordinates playerLoc;  ChunkCoordinates doesn't exist
   		//private TickEvent.WorldTickEvent worldEvent;
   		//private TickEvent.PlayerTickEvent playerEvent;
   		//private ChunkCoordinates[] playerLocation;
   		private int lastPosNum;
   		//these are used to keep track of all of the players locations
   		private double posXofPlayer;
   		private double posZofPlayer;
   		private int posXofLava;
   		private int currentTick;
   		private boolean initialize;

   		private HudRectangle timerRectangle;
   		private HudString timerString;
   		private int seconds;
   		private static Clock clock;
   		private String secondsString;
   		private int startTime;
   		private double posYofPlayer;
   		private long TIME;
   		private Clock fireClock;
   		private long fireTIME;

   		private int numMsForFireToSpawn;

    public Minequest()

    {
      			super();
      //			//initializing all private variables
      //			progress = 0;
      	//		name = "MinerQuest";
      //			completed = false; all three are variables of parent class which are different now

      			pleaseStop = false;
      			//playerLoc = new ChunkCoordinates();
      			//worldEvent = new TickEvent.WorldTickEvent(null, null, null);
      			//playerEvent = new TickEvent.PlayerTickEvent(null, null);
      			///playerLocation = new ChunkCoordinates[3];
      			posXofPlayer = 0;
      			posZofPlayer = 0;
      			posYofPlayer = 0;
      			lastPosNum = 0;
      			posXofLava = -40;
      			currentTick = 0;
      			timerRectangle = new HudRectangle(-25, 5, 50, 30, 0xff0000ff, true, false);
      			clock = Clock.systemDefaultZone();
      			fireClock = Clock.systemDefaultZone();
      			seconds = 60;
      			secondsString += seconds;
      			timerString = new HudString(0, 10, secondsString, 2.0f ,true,false);
     // 			register();  doesn't exist prob parent class function
            numMsForFireToSpawn = 180;

        //  instructionTextureLocations = new ResourceLocation[]
        //     				{
        //     				new ResourceLocation(BiGX.TEXTURE_PREFIX, "textures/GUI/instructions/MinerQuestInstruction1.png"),
        //     				new ResourceLocation(BiGX.TEXTURE_PREFIX, "textures/GUI/instructions/MinerQuestInstruction2.png"),
        //     				new ResourceLocation(BiGX.TEXTURE_PREFIX, "textures/GUI/instructions/MinerQuestInstruction3.png"),
        //     				};
        //  instructionStringContents = new String[]
        //         {
        //                 "Run from the fire",
        //                 "Collect Gold",
        //                 "Stay alive until time runs out",
        //         };
    // double check if instructions work
        this.DIMID = WorldProviderMiner.DIM_ID;
        this.questStartLocation = new Vec3d(0,4,0);
        this.isStarted = false;
        this.isWaiting = false;
    }

    		// public void onItemPickUp(EntityItemPickupEvent event) EntityItemPickupEvent doesn't exist
    		// {
    		// 	//QuestTeleporter.teleport(player, 0, (int) MinerNPC.LOCATION.xCoord, (int) MinerNPC.LOCATION.yCoord, (int) MinerNPC.LOCATION.zCoord);
    		// 	//super.complete();
        //
    		// }

        enum direction     //<figure out what this does
        		{
        //			NORTH,
        //			WEST;
        		}
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {
        return;
    }

    @Override
    public boolean onPlayerJoin(EntityPlayer player) {
        System.out.println("Player attempting to join");
        if(isStarted)
        {
          setupQuestEnv(player.world, player);
          ServerUtils.sendQuestData(EnumPacketServer.QuestJoinFailed,(EntityPlayerMP)player, Long.toString(this.waitTime));
             isWaiting = true;
             return false;
         }else{
             if (!isWaiting)
             {
                 WorldServer ws = DimensionManager.getWorld(this.DIMID);
                 isWaiting = true;
             }
             ServerUtils.telport((EntityPlayerMP)player, this.questStartLocation,this.DIMID);
             return true;
        }
    }

    @Override
    public void start(EntityPlayerMP playerMP) {
        //this.player = player;

        System.out.println(" Start Obama quest ");

        System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
      ServerUtils.telport((EntityPlayerMP) playerMP, questStartLocation, DIMID);
    }

   // public void onQuit(ClientDisconnectionFromServerEvent event)  //<ClientDisconnectionFromServerEvent doesn't exist
   //  		{
   //
   //  			QuestTeleporter.teleport(player, 0, (int) MinerNPC.LOCATION.xCoord, (int) MinerNPC.LOCATION.yCoord, (int) MinerNPC.LOCATION.zCoord);
   //  		}
   //  	static boolean worldLoaded = false;
   //    public void onWorldLoadEvent(WorldEvent.Load event) //<WorldEvent doesn't exist
   //  		{
   //  			if (event.world.provider.dimensionId == MINERUNDIMENSIONID) //<world doesn't exist
   //  				worldLoaded = true;
   //  			initialize = false;
   //  			posXofLava = -40;
   //  			currentTick = 0;
   //  			HudManager.registerRectangle(timerRectangle); //<HudManager doesn't exist
   //  			HudManager.registerString(timerString);
   //  		}
   //
   //   		private void clearStartPotHoles(int startx, int startz, World world)
   //    		{
   //    				for(int x = startx; x < startx+200; x++)
   //    				{
   //    					for(int z = 0; z < 11; z++)
   //    					{
   //    						// Random rand = new Random(); //<Random doesn't exist
   //    						// int randomInt = rand.nextInt(10); <nextInt is not a function
   //    						 // switch(randomInt)
   //    						{
   //    						case 0:
   //    							world.setBlock((int) (startx)+x, 20, (int) (startz+z), Blocks.iron_ore); //<setBlock doesn't exist and Blocks doesn't exist
   //    						break;
   //    						case 1:
   //    							world.setBlock((int) (startx)+x, 20, (int) (startz+z), Blocks.gold_ore);
   //    						break;
   //    						case 2:
   //    							world.setBlock((int) (startx)+x, 20, (int) (startz+z), Blocks.diamond_ore);
   //    						break;
   //    						case 3:
   //    							world.setBlock((int) (startx)+x, 20, (int) (startz+z), Blocks.redstone_ore);
   //    						break;
   //    						case 4:
   //    							world.setBlock((int) (startx)+x, 20,(int) (startz+z), Blocks.glowstone);
   //    						break;
   //    						default:
   //    							world.setBlock((int) (startx)+x, 20, (int) (startz+z), Blocks.stone);
   //    						break;
   //    						}
   //    						//set it to air above
   //    						world.setBlock((int) startx + x, 21, (int) (startz + z), Blocks.air);
   //    					}
   //    				}
   //    		}
   //
   //      private void clearPotHoles(int startx, int startz, World world)
   //      		{
   //      			for(int z = 0; z < 11; z++)
   //      			{
   //
   //      					 Random rand = new Random(); //<Random doesn't exist
   //      					int randomInt = rand.nextInt(10);  <nextInt is not a function
   //      				  switch(randomInt)
   //      					{
   //      					case 0:
   //      						world.setBlock((int) (startx), 20, (int) (startz+z), Blocks.iron_ore); //<setBlock doesn't exist and Blocks doesn't exist
   //      					break;
   //      					case 1:
   //      						world.setBlock((int) (startx), 20, (int) (startz+z), Blocks.gold_ore);
   //      					break;
   //      					case 2:
   //      						world.setBlock((int) (startx), 20, (int) (startz+z), Blocks.diamond_ore);
   //      					break;
   //      					case 3:
   //      						world.setBlock((int) (startx), 20, (int) (startz+z), Blocks.redstone_ore);
   //      					break;
   //      					case 4:
   //      						world.setBlock((int) (startx), 20,(int) (startz+z), Blocks.glowstone);
   //      					break;
   //      					default:
   //      						world.setBlock((int) (startx), 20, (int) (startz+z), Blocks.stone);
   //      					break;
   //      				}
   //      			}

    @Override
    public Vec3d getStartLocation()
    {
      return questStartLocation;
    }

    @Override
    public void start(){
      TIME = clock.millis();
    }

    @Override
    public void start(EntityPlayerSP player) {
      if(isStarted)
        {
          System.out.println("Game Already Started Going to Waiting");
        }
    }

    @Override
    public void start(EntityJoinWorldEvent event) {

    }

    @Override
    public void end() {
        System.out.println(" Start Miner quest ");
    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {
    }
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    }
    // public void onWorldTick(TickEvent.WorldTickEvent event) {
    //   			 World world= event.world.provider.worldObj; //<.worldObj doesn't exist
    //   			// if (event.world.provider.worldObj.isRemote)
    //   			// 	return;
    //
    //   			if(worldLoaded && event.world.provider.dimensionId == WorldProviderMiner.MINERUNDIMENSIONID && !pleaseStop) //<dimensionId doesn't work
    //   			{
    //         //
    //         //
    //   			 	if (super.showingInstructions()) //showingInstructions doesn't exist
    //   			 	{
    //   			 		resetToAirStart(posXofPlayer - 1,0,world); // resetToAirStart doesn't exist, or world variable
    //   			 		resetToAirStart(posXofPlayer - 1,0,world);
    //   			 		BiGX.instance().clientContext.lock(true); //BiGX does not exist
    //   			 		return;
    //   			 	}
    //   			 	else
    //   			 	{
    //   			 		TIME = clock.millis();
    //   			 		BiGX.instance().clientContext.lock(false);
    //   			 	}
    //
    //
    //   					if(posXofPlayer-35>lastPosNum)
    //   						{
    //   						int secondsPassed = (int)((clock.millis()-TIME)/1000);
    //   						if(secondsPassed == seconds||posYofPlayer<19)
    //   						{
    //   							super.complete();
    //   							HudManager.unregisterRectangle(timerRectangle);
    //   							HudManager.unregisterString(timerString);
    //   							currentTick = 0;
    //   							QuestTeleporter.teleport(player, 0, (int) MinerNPC.LOCATION.xCoord, (int) MinerNPC.LOCATION.yCoord, (int) MinerNPC.LOCATION.zCoord);
    //   							initialize = false;
    //   						}
    //   						timerString.text = ""+(seconds - secondsPassed);
    //   						generateWestWall(5,0,world);
    //   						if(!initialize)
    //   						{
    //   							resetToAirStart(-40,0,world);
    //   							clearStartPotHoles(0,0,world);
    //   						9	initialize = true;
    //   						}
    //   						resetToAirCont(posXofPlayer+50,0,world);
    //   						resetToAirCont(posXofPlayer+50,0,world);
    //   						clearPotHoles((int) (posXofPlayer+50),0,world);
    //   						clearPotHoles((int) (posXofPlayer+50),0,world);
    //   						if(posXofPlayer - posXofLava >=40)
    //   						{
    //   							if (clock.millis() - fireTIME >= numMsForFireToSpawn)
    //   							{
    //   								generateLavaWall(posXofLava, 0, world);
    //   								fireTIME = clock.millis();
    //   								posXofLava++;
    //   							}
    //   						}
    //   						else
    //   						{
    //   							if (clock.millis() - fireTIME >= numMsForFireToSpawn)
    //   							{
    //   								generateLavaWall(posXofLava, 0, world);
    //   								fireTIME = clock.millis();
    //   								posXofLava++;
    //   							}
    //   						}
    //
    //   						generateFloorAndCeiling(posXofPlayer-1,0,world);
    //   						generateWalls((int) (posXofPlayer)-1,posZofPlayer,world);
    //   						Random rand = new Random();
    //   						Random rand2 = new Random();
    //   						int randomInt2 = rand2.nextInt(8);
    //   						int randomInt = rand.nextInt(150);
    //   						int randomInt3 = rand.nextInt(300);
    //   						int randomInt4 = rand2.nextInt(7)+2;
    //   						int randomInt5 = rand2.nextInt(1000);
    //   						switch(randomInt)
    //   						{
    //   							case 1:
    //   							generateEmeraldWall(posXofPlayer+20,0,world);
    //   							break;
    //   							case 2:
    //   							createPotHole(posXofPlayer+20,randomInt2+2,world);
    //   							case 3:
    //   							case 4:
    //   							case 5:
    //   							//EntityItem entityitem1 = new EntityItem(event.world.provider.worldObj, posXofPlayer+20, 21, randomInt4, new ItemStack(Items.gold_ingot,1));
    //   							//event.world.provider.worldObj.spawnEntityInWorld(entityitem1);
    //   							break;
    //   						}
    //   						switch(randomInt5)
    //   						{
    //   							case 1:
    //   							EntityItem entityitem1 = new EntityItem(event.world.provider.worldObj, posXofPlayer+20, 21, randomInt4, new ItemStack(Items.gold_ingot,1));
    //   							event.world.provider.worldObj.spawnEntityInWorld(entityitem1);
    //   							break;
    //
    //   						}
    //   						if (posXofPlayer % 2 == 0)
    //   						{
    //   							switch(randomInt3)
    //   							{
    //
    //   								case 1:
    //   								case 3:
    //   								if(!isWall(posXofPlayer+20, 0, world)&&!isWall(posXofPlayer+19, 0, world))
    //   								{
    //   									generateObstacleWall(posXofPlayer+20, 0, world);
    //   								}
    //   								break;
    //   								case 2:
    //   								case 4:
    //   								if(!isWall(posXofPlayer+20, 5, world)&&!isWall(posXofPlayer+19, 5, world))
    //   									{
    //   										generateObstacleWall(posXofPlayer+20, 5, world);
    //   									}
    //   								break;
    //
    //   							}
    //   						}
    //   			} // end init



    // private boolean isWall(double startx, double startz, World world)
    // 		{
    // 			for(int z = (int) startz; z < startz+6; z++)
    // 			{
    // 				for(int y = 21; y < 24; y++)
    // 				{
    // 					 if(world.getBlock((int) startx, y, z).equals(Blocks.cobblestone)) <getBlock doesn't exist, Blocks doesn't exist
    // 					 {
    // 						 return true;
    // 					 }
    // 				}
    // 			}
    // 			return false;
    //
    // 		}

    		// private void resetToAirCont(double startx, double startz, World world)
    		// {
        //
    		// 		for(int z = (int) startz+1; z < startz+10; z++)
    		// 		{
    		// 			for(int y = 21; y < 24; y++)
    		// 			{
    		// 				 world.setBlock((int) startx, y, z, Blocks.air); //setBlock and Blocks not working
    		// 			}
    		// 		}
    		// }

        	//     public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
        	//     {
        	//         posXofPlayer = event.player.getPlayerCoordinates().posX; //getPlayerCoordinates doesn't work
        	//         posZofPlayer = event.player.getPlayerCoordinates().posZ;
        	//         posYofPlayer = event.player.getPlayerCoordinates().posY;
        	// 		if(event.player.isBurning())
        	// 		{
        	// 			super.complete(); //super doesn't have complete
        	// 			HudManager.unregisterRectangle(timerRectangle); //Hud manager doesn't work
        	// 			HudManager.unregisterString(timerString);
        	// 			currentTick = 0;
        	// 			QuestTeleporter.teleport(player, 0, (int) Elon.LOCATION.xCoord, (int) Elon.LOCATION.yCoord, (int) Elon.LOCATION.zCoord); //Questteleporter doesn't exist or elon's specific coords
        	// 			seconds = 60;
        	// 			initialize = false;
        	// 		}
        	// //		playerLoc = event.player.getPlayerCoordinates();
        	// //        playerLocation[1] = playerLoc;
        	// 		//System.out.println("ticking");
        	//         if(event.player.capabilities.allowEdit)
        	//             event.player.setGameType(WorldSettings.getGameTypeById(2)); //WorldSettings doesn't exist either
        	//         //playerEvent = event;
          //
          //
        	//     }

              //
          		// private void generateEmeraldWall(double startx, double startz, World world)
          		// {
          		// 	for(int z = 0; z < 11; z++)
          		// 	{
          		// 		if(world.isAirBlock((int) (startx), 21, (int) (startz+z)))
          		// 		{
              //
          		// 		 world.setBlock((int) (startx), 21, (int) (startz+z), Blocks.emerald_ore); //Blocks doesn't exist
          		// 		}
          		// 	}
          		// }
          		// private void generateObstacleWall(double startx, double startz, World world)
          		// {
              //
          		// 	for(int z = (int) startz; z < startz+6; z++)
          		// 	{
          		// 		for(int y = 21; y < 24; y++)
          		// 		{
          		// 			 world.setBlock((int) startx, y, z, Blocks.cobblestone); //Blocks doesn't exist
          		// 		}
          		// 	}
          		// }


              //		private void createPotHole(double startx, double startz, World world)
              //		{
              //			for(int x = (int) startx; x < startx+3; x++)
              //			{
              //				for(int z = (int) startz; z < startz+3; z++)
              //				{
              //					 world.setBlock(x, 20, z, Blocks.lava); //Blocks doesn't exist
              //				}
              //			}
              //		}


              	//Generation of normal floor and ceiling north
              		// private void generateFloorAndCeiling(double startx, double startz, World world)
              		// {
              		// 	for(int x = 0; x < 30; x++)
              		// 	{
              		// 		for(int z = 0; z < 11; z++)
              		// 		{
              		// 			Random rand = new Random(); //random doesn't exist
              		// 			int randomInt = rand.nextInt(10); //nextInt doesn't exist
              		// 			if(world.isAirBlock((int) (startx+x), 20, (int) (startz+z)))
              		// 			//|| world.getBlock((int) (startx+x), 20, (int) (startz+z)).equals(Blocks.lava))
              		// 			{
                  //
              		// 			switch(randomInt)
              		// 			{
              		// 			case 0:
              		// 			 world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.iron_ore); //setBlock and Blocks don't exist
              		// 			break;
              		// 			case 1:
              		// 				world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.gold_ore);
              		// 			break;
              		// 			case 2:
              		// 				world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.diamond_ore);
              		// 			break;
              		// 			case 3:
              		// 				world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.redstone_ore);
              		// 			break;
              		// 			case 4:
              		// 				world.setBlock((int) (startx+x), 20,(int) (startz+z), Blocks.glowstone);
              		// 			break;
              		// 			default:
              		// 				world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.stone);
              		// 			break;
              		// 			}
              		// 			}
              		// 			if(world.isAirBlock((int) (startx+x), 25, (int) (startz+z)))
              		// 			{
                  //
              		// 				randomInt = rand.nextInt(10);
              		// 			switch(randomInt)
              		// 			{
              		// 			case 0:
              		// 				world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.iron_ore);
              		// 			break;
              		// 			case 1:
              		// 				world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.gold_ore);
              		// 			break;
              		// 			case 2:
              		// 				world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.diamond_ore);
              		// 			break;
              		// 			case 3:
              		// 				world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.redstone_ore);
              		// 			break;
              		// 			case 4:
              		// 				world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.glowstone);
              		// 			break;
              		// 			//case 5:
              		// 			//world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.air);
              		// 			//break;
              		// 			default:
              		// 				world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.stone);
              		// 			break;
              		// 			}
              		// 			}
              		// 		}
              		// 	}//end outer for loop
              		// }//end function



                //	//generate walls going north
                		// private void generateWalls(int startx, double startz, World world)
                		// {
                    //
                		// 	for(int x = 0; x < 30 ; x++)
                		// 	{
                		// 		for(int y = 20; y < 25; y++)
                		// 		{
                		// 			if(world.isAirBlock((int) (startx+x), y, 0))
                		// 			//		|| world.getBlock((int) (startx+x), y, 0).equals(Blocks.lava))
                		// 			{
                		// 			Random rand = new Random();
                		// 			int randomInt = rand.nextInt(10);
                		// 			switch(randomInt)
                		// 			{
                		// 			case 0:
                		// 				world.setBlock(startx+x, y, 0, Blocks.iron_ore); //<Blocks and setBlock don't exist
                		// 			break;
                		// 			case 1:
                		// 				world.setBlock(startx+x, y, 0, Blocks.gold_ore);
                		// 			break;
                		// 			case 2:
                		// 				world.setBlock(startx+x, y, 0, Blocks.diamond_ore);
                		// 			break;
                		// 			case 3:
                		// 				world.setBlock(startx+x, y, 0, Blocks.redstone_ore);
                		// 			break;
                		// 			case 4:
                		// 				world.setBlock(startx+x, y, 0, Blocks.glowstone);
                		// 			break;
                		// 			default:
                		// 				world.setBlock(startx+x, y, 0, Blocks.stone);
                		// 			break;
                		// 			}
                		// 			switch(randomInt)
                		// 			{case 0:
                		// 				world.setBlock(startx+x, y, 10, Blocks.iron_ore);
                		// 			break;
                		// 			case 1:
                		// 				world.setBlock(startx+x, y, 10, Blocks.gold_ore);
                		// 			break;
                		// 			case 2:
                		// 				world.setBlock(startx+x, y, 10, Blocks.diamond_ore);
                		// 			break;
                		// 			case 3:
                		// 				world.setBlock(startx+x, y, 10, Blocks.redstone_ore);
                		// 			break;
                		// 			case 4:
                		// 				world.setBlock(startx+x, y, 10, Blocks.glowstone);
                		// 			break;
                		// 			default:
                		// 				world.setBlock(startx+x, y, 10, Blocks.stone);
                		// 			break;
                		// 			}
                		// 			}
                		// 		}
                		// 	}
                		// }//end function

                    //	//	private void duplicateWalls(int amnt, int x, int y, int z, TickEvent.WorldTickEvent event)
                    //	//		{
                    //	//		World world = event.world.provider.worldObj;
                    //	//		for(int c = 0; c < amnt; c++)
                    //	//			{
                    //	//				generateWalls(x+(c*5), z, event);
                    //	//			}
                    //	//		}


                    // generate walls but going to the right
                    		// private void generateWallsWest(int startx, double startz, World world)
                    		// {
                        //
                    		// 	for(int x = 0; x < 10 ; x++)
                    		// 	{
                    		// 		for(int y = 20; y < 25; y++)
                    		// 		{
                    		// 			if(world.isAirBlock((int) (startx+x), 20, (int) (startz+x)))
                    		// 			{
                    		// 			Random rand = new Random(); // random doesn't exist
                    		// 			int randomInt = rand.nextInt(10); //nextInt doesn't exist
                    		// 			switch(randomInt)
                    		// 			{
                    		// 			case 0:
                    		// 				world.setBlock(0, y, (int) (startz+x), Blocks.iron_ore); //<Blocks and setBlock don't exist
                    		// 			break;
                    		// 			case 1:
                    		// 				world.setBlock(0, y, (int) (startz+x), Blocks.gold_ore);
                    		// 			break;
                    		// 			case 2:
                    		// 				world.setBlock(0, y, (int) (startz+x), Blocks.diamond_ore);
                    		// 			break;
                    		// 			case 3:
                    		// 				world.setBlock(0, y, (int) (startz+x), Blocks.redstone_ore);
                    		// 			break;
                    		// 			case 4:
                    		// 				world.setBlock(0, y, (int) (startz+x), Blocks.glowstone);
                    		// 			break;
                    		// 			default:
                    		// 				world.setBlock(0, y, (int) (startz+x), Blocks.stone);
                    		// 			break;
                    		// 			}
                    		// 			switch(randomInt)
                    		// 			{case 0:
                    		// 				world.setBlock(10, y, (int) (startz+x), Blocks.iron_ore);
                    		// 			break;
                    		// 			case 1:
                    		// 				world.setBlock(10, y, (int) (startz+x), Blocks.gold_ore);
                    		// 			break;
                    		// 			case 2:
                    		// 				world.setBlock(10, y, (int) (startz+x), Blocks.diamond_ore);
                    		// 			break;
                    		// 			case 3:
                    		// 				world.setBlock(10, y, (int) (startz+x), Blocks.redstone_ore);
                    		// 			break;
                    		// 			case 4:
                    		// 				world.setBlock(10, y, (int) (startz+x), Blocks.glowstone);
                    		// 			break;
                    		// 			default:
                    		// 				world.setBlock(10, y, (int) (startz+x), Blocks.stone);
                    		// 			break;
                    		// 			}
                    		// 			}
                    		// 		}
                    		// 	}
                    		// }//end function

                        //		//generateFloor and ceiling with the direction going right
                        	// 	private void generateFloorAndCeilingWest(double startx, double startz, World world)
                        	// 	{
                        	// 		for(int x = 0; x < 11; x++)
                        	// 		{
                        	// 			for(int z = 0; z < 10; z++)
                        	// 			{
                          //
                        	// 				if(world.isAirBlock((int) (startx+x), 20, (int) (startz+z)))
                        	// 				{
                        	// 				Random rand = new Random();//random doesn't exist
                        	// 				int randomInt = rand.nextInt(10); //nextInt doesn't exist
                        	// 				switch(randomInt)
                        	// 				{
                        	// 				case 0:
                        	// 				 world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.iron_ore); //<Blocks and setBlock don't work
                        	// 				break;
                        	// 				case 1:
                        	// 					world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.gold_ore);
                        	// 				break;
                        	// 				case 2:
                        	// 					world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.diamond_ore);
                        	// 				break;
                        	// 				case 3:
                        	// 					world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.redstone_ore);
                        	// 				break;
                        	// 				case 4:
                        	// 					world.setBlock((int) (startx+x), 20,(int) (startz+z), Blocks.glowstone);
                        	// 				break;
                        	// 				default:
                        	// 					world.setBlock((int) (startx+x), 20, (int) (startz+z), Blocks.stone);
                        	// 				break;
                        	// 				}
                        	// 				randomInt = rand.nextInt(10);
                        	// 				switch(randomInt)
                        	// 				{
                        	// 				case 0:
                        	// 					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.iron_ore);
                        	// 				break;
                        	// 				case 1:
                        	// 					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.gold_ore);
                        	// 				break;
                        	// 				case 2:
                        	// 					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.diamond_ore);
                        	// 				break;
                        	// 				case 3:
                        	// 					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.redstone_ore);
                        	// 				break;
                        	// 				case 4:
                        	// 					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.glowstone);
                        	// 				break;
                        	// //				case 5:
                        	// //					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.air);
                        	// //				break;
                        	// 				default:
                        	// 					world.setBlock((int) (startx+x), 25, (int) (startz+z), Blocks.stone);
                        	// 				break;
                        	// 				}
                        	// 				}
                        	// 			}
                        	// 		}//end outer for loop
                        	// 	}//end function

                          //		//master function for generation of single walls
                          		// private void generateWall(double startx, double startz, World world, direction dir)
                          		// {
                          		// 	switch(dir)
                          		// 	{
                          		// 	case NORTH: //north doesn't exist
                          		// 		generateNorthWall(startx, startz, world); //function doesn't exist
                          		// 	break;
                          		// 	case WEST://west doesn't exist
                          		// 		generateWestWall(startx, startz, world); //function doesn't exist
                          		// 	break;
                              //
                          		// 	}
                          		// }

                              //		//sub function for generation of north walls
                              		// private void generateNorthWall(double startx, double startz, World world)
                              		// {
                              		// 	for(int x = 0; x < 10 ; x++)
                              		// 	{
                              		// 		for(int y = 20; y < 25; y++)
                              		// 		{
                              		// 			world.setBlock((int) (startx+x), y, 10, Blocks.stone); //setBlock and Blocks don't exist
                              		// 		}
                              		// 	}
                              		// }

                                     // private void generateWestWall(double startx, double startz, World world)
                                     // {
                                     //   for(int x = 0; x < 10 ; x++)
                                     //   {
                                     //     for(int y = 20; y < 25; y++)
                                     //     {
                                     //       world.setBlock((int) startx, y, (int) (startz+x), Blocks.obsidian);//blocks and setblock doesn't exist
                                     //     }
                                     //   }
                                     // }

                                     //    public void setDifficulty(Difficulty difficultyIn) {
                                     //      // TODO Auto-generated method stub
                                     //
                                     //    }

}
