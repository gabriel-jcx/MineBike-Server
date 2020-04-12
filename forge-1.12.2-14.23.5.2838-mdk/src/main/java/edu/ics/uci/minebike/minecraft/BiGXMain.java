package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.client.HudManager;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.serverSave.ServerSaveManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import org.apache.logging.log4j.Logger;
@Mod(modid = BiGXMain.MOD_ID,name = BiGXMain.MOD_NAME, version = BiGXMain.MOD_VERSION, dependencies = "required-after:soccer")
public class BiGXMain {
    public static final String MOD_ID = "minebikemod";
    public static final String MOD_NAME = "MineBike Mod";
    public static final String MOD_VERSION = "0.1.0";
    private static Logger logger;
    public static CommonEventHandler handler = new CommonEventHandler();
    public static CommonProxy proxy;// = new CommonProxy();
    public static ServerSaveManager saveManager = new ServerSaveManager();
    public static CustomQuestManager questMangager;
//    FMLne
    public HudManager hudManager;
    public static FMLEventChannel Channel;
    public static FMLEventChannel ChannelPlayer;
    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event){


        proxy = new CommonProxy();
        Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("MineBikeServer");
        ChannelPlayer = NetworkRegistry.INSTANCE.newEventDrivenChannel("MineBikeClient");


        // Also need to load the data from the save here and sent the packet to the client
        System.out.println("Preinitializing - Finish registering the Custom Dimensions");
        // preInit goes here
        System.out.printf("MineBike: PreInit finished");
        logger = event.getModLog();



        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        MinecraftForge.EVENT_BUS.register(handler);
        MinecraftForge.EVENT_BUS.register(proxy);
        proxy.load();
        logger.info("MineBike: PreInit finished");
    }


    @EventHandler
    public void init(FMLInitializationEvent event){
        // Initialization goes here!
        logger.info("Initalization Started");
        if(event.getSide().isClient()){
            hudManager = HudManager.getInstance(Minecraft.getMinecraft());
            MinecraftForge.EVENT_BUS.register(hudManager);

        }

        logger.info("MineBike: Init finished");

        System.out.println();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        // Post initialization codes goes here!
        //handler = new CommonEventHandler();

        logger.info("MineBike: PostInit finished");
        questMangager = new CustomQuestManager();
    }

}
