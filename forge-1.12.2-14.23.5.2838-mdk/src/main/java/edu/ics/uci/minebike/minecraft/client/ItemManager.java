package edu.ics.uci.minebike.minecraft.client;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
import edu.ics.uci.minebike.minecraft.client.item.ItemGameFishingRod;
import net.theawesomegem.fishingmadebetter.common.item.fishingrod.ItemBetterFishingRod;

import static net.theawesomegem.fishingmadebetter.common.item.ItemManager.FISHING_ROD_DIAMOND;

public class ItemManager {
    public static final ItemBetterFishingRod GAME_FISHING_ROD = new ItemGameFishingRod("game_rod", 20,5,1);


    public ItemManager() {
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(new Item[]{GAME_FISHING_ROD});
        }


    public static void registerModels() {
        GAME_FISHING_ROD.registerItemModel(GAME_FISHING_ROD
        );

    }
}
