package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;

public class BurgerAll  extends ItemFood {
    public BurgerAll() {
        super(20, 15f, false);
        setUnlocalizedName("burger_all");
        setRegistryName("burger_all");
        setCreativeTab(CreativeTabs.FOOD);
    }

    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}