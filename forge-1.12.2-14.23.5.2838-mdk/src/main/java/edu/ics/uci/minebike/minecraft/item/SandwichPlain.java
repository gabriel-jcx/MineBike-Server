package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;

public class SandwichPlain  extends ItemFood {
    public SandwichPlain() {
        super(14, 10f, false);
        setUnlocalizedName("sandwich_plain");
        setRegistryName("sandwich_plain");
        setCreativeTab(CreativeTabs.FOOD);
    }

    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}