package edu.ics.uci.minebike.minecraft.item;


import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
public class CookBurgerBun extends ItemFood {
    public CookBurgerBun()
    {
        super(2,1.2f,false);
        setUnlocalizedName("hamburger_bun");
        setRegistryName("hamburger_bun");
//        ModelLoader.setCustomModelResourceLocation(this, 0, );
    }
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
