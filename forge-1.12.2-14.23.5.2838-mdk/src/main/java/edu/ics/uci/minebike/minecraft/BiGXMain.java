package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.serverSave.ServerSaveManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Logger;
@Mod(modid = BiGXMain.MOD_ID,name = BiGXMain.MOD_NAME, version = BiGXMain.MOD_VERSION)
public class BiGXMain {
    public static final String MOD_ID = "minebikemod";
    public static final String MOD_NAME = "MineBike Mod";
    public static final String MOD_VERSION = "0.1.0";
    private static Logger logger;
    CommonEventHandler handler = new CommonEventHandler();
    CommonProxy proxy = new CommonProxy();
    ServerSaveManager saveManager = new ServerSaveManager();
    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event){
        // preInit goes here
        System.out.printf("MineBike: PreInit finished");
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(handler);
        logger.info("MineBike: PreInit finished");
    }
    @EventHandler
    public void init(FMLInitializationEvent event){
        // Initialization goes here!
        logger.info("Initalization Started");
        logger.info("MineBike: Init finished");

        System.out.println();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        // Post initialization codes goes here!
        //handler = new CommonEventHandler();

        logger.info("MineBike: PostInit finished");
    }

}
