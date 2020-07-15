package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CookLettuce  extends ItemFood {
    public CookLettuce()
    {
        super(2,1.2f,false);
        setUnlocalizedName("lettuce");
        setRegistryName("lettuce");
        setCreativeTab(CreativeTabs.FOOD);
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
