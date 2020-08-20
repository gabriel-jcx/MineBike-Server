package edu.ics.uci.minebike.minecraft.item;

import akka.dispatch.SaneRejectedExecutionHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemManager {
    public static final ItemGameFishingRod GAME_FISHING_ROD = new ItemGameFishingRod();
    public static final HamburgerBun HAMBURGER_BUN = new HamburgerBun();
    public static final BurgerAll BURGER_ALL = new BurgerAll();
    public static final BurgerMeat BURGER_MEAT = new BurgerMeat();
    public static final BurgerNopot BURGER_NOPOT = new BurgerNopot();
    public static final BurgerPlain BURGER_PLAIN = new BurgerPlain();
    public static final BurgerVeg BURGER_VEG = new BurgerVeg();
    public static final SandwichBread SANDWICH_BREAD = new SandwichBread();
    public static final SandwichAll SANDWICH_ALL = new SandwichAll();
    public static final SandwichMeat SANDWICH_MEAT = new SandwichMeat();
    public static final SandwichNopot SANDWICH_NOPOT = new SandwichNopot();
    public static final SandwichPlain SANDWICH_PLAIN = new SandwichPlain();
    public static final SandwichVeg SANDWICH_VEG = new SandwichVeg();
    public static final Lettuce LETTUCE = new Lettuce();
    public static final LettuceHead LETTUCE_HEAD = new LettuceHead();
    public static final SlicedBeef SLICED_BEEF = new SlicedBeef();
    public static final SlicedChicken SLICED_CHICKEN = new SlicedChicken();


    public ItemManager() {
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(GAME_FISHING_ROD,BURGER_ALL,BURGER_MEAT,BURGER_NOPOT,BURGER_PLAIN,BURGER_VEG,SANDWICH_ALL,SANDWICH_MEAT,SANDWICH_NOPOT,SANDWICH_PLAIN,SANDWICH_VEG,HAMBURGER_BUN,SANDWICH_BREAD,LETTUCE,LETTUCE_HEAD,SLICED_BEEF,SLICED_CHICKEN);
    }


    public static void registerModels() {
        GAME_FISHING_ROD.registerItemModel(GAME_FISHING_ROD);
        HAMBURGER_BUN.registerItemModel(HAMBURGER_BUN);
        BURGER_ALL.registerItemModel(BURGER_ALL);
        BURGER_MEAT.registerItemModel(BURGER_MEAT);
        BURGER_NOPOT.registerItemModel(BURGER_NOPOT);
        BURGER_PLAIN.registerItemModel(BURGER_PLAIN);
        BURGER_VEG.registerItemModel(BURGER_VEG);
        SANDWICH_BREAD.registerItemModel(SANDWICH_BREAD);
        SANDWICH_ALL.registerItemModel(SANDWICH_ALL);
        SANDWICH_MEAT.registerItemModel(SANDWICH_MEAT);
        SANDWICH_NOPOT.registerItemModel(SANDWICH_NOPOT);
        SANDWICH_PLAIN.registerItemModel(SANDWICH_PLAIN);
        SANDWICH_VEG.registerItemModel(SANDWICH_VEG);
        LETTUCE.registerItemModel(LETTUCE);
        LETTUCE_HEAD.registerItemModel(LETTUCE_HEAD);
        SLICED_BEEF.registerItemModel(SLICED_BEEF);
        SLICED_CHICKEN.registerItemModel(SLICED_CHICKEN);
    }
}
