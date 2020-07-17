package edu.ics.uci.minebike.minecraft.item;

import akka.dispatch.SaneRejectedExecutionHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
import edu.ics.uci.minebike.minecraft.item.ItemGameFishingRod;

public class ItemManager {
    public static final ItemGameFishingRod GAME_FISHING_ROD = new ItemGameFishingRod();
    public static final CookBurgerBun BURGER_BUN = new CookBurgerBun();
    public static final CookBurger BURGER = new CookBurger();
    public static final CookSandwichBread SANDWICH_BREAD = new CookSandwichBread();
    public static final CookSandwich SANDWICH = new CookSandwich();
    public static final CookLettuce LETTUCE = new CookLettuce();


    public ItemManager() {
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(new Item[]{GAME_FISHING_ROD,BURGER,BURGER_BUN, SANDWICH,SANDWICH_BREAD,LETTUCE});
    }


    public static void registerModels() {
        System.out.println("Registering the Game_Fishing ROD!!!");
        System.out.println("Registry location of Game_Fishing_ROD is " + GAME_FISHING_ROD.getRegistryName());
        GAME_FISHING_ROD.registerItemModel(GAME_FISHING_ROD);
        BURGER_BUN.registerItemModel(BURGER_BUN);
        BURGER.registerItemModel(BURGER);
        SANDWICH_BREAD.registerItemModel(SANDWICH_BREAD);
        SANDWICH.registerItemModel(SANDWICH);
        LETTUCE.registerItemModel(LETTUCE);

        //ModelLoader.setCustomModelResourceLocation(GAME_FISHING_ROD, 0, new ModelResourceLocation(GAME_FISHING_ROD.getRegistryName(), "inventory"));


    }
}
